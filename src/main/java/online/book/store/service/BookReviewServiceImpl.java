package online.book.store.service;

import online.book.store.dao.BookDao;
import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.BookReview;
import online.book.store.entity.Category;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookReviewServiceImpl implements BookReviewService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Override
    public void addReview(BookReviewDto bookReviewDto, User user) {
        String isbn = bookReviewDto.getIsbn();
        BookReview bookReview = bookReviewDto.doBookReviewBuilder();
        Book book = this.bookService.getBookByIsbn(isbn);
        user.addReview(bookReview);
        book.addReview(bookReview);
        this.userService.updateUser(user);
        this.bookService.updateBook(book);
        averageRatings(book, book.getCategories());
    }

    @Override
    public BookReviewDto reviewExist(Book book, User user) {
        BookReviewDto bookReviewDto = new BookReviewDto();
        if(this.bookDao.reviewExist(book.getId(), user.getId())){
            String username = user.getUsername();
            bookReviewDto.setAuthor(username);
        }
        return bookReviewDto;
    }

    private void averageRatings(Book book, List<Category> booksCategories) {
        int bookId = book.getId();
        double rating = round(this.bookDao.averageRating(bookId));
        book.setBookRating(rating);

        booksCategories.forEach((category -> {
            List<Book> books = category.getBooks();
            category.setRating(calculateAvgCategoryRating(books));
        }));

        this.bookService.updateBook(book);

    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 1);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


    private double calculateAvgCategoryRating(List<Book> books){
        double rating = 0d;
        int size = books.size();
        for(Book book : books){
            rating += book.getBookRating();
        }
        return round(rating / size);
    }



}
