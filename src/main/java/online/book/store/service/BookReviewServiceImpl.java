package online.book.store.service;

import online.book.store.dao.BookDao;
import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.BookReview;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        BookReview bookReview = bookReviewDto.doBookReviewBuilder();
        Book book = this.bookService.getBookByIsbn(bookReviewDto.getIsbn());
        user.addReview(bookReview);
        book.addReview(bookReview);
        this.userService.updateUser(user);
        this.bookService.updateBook(book);
        addAvgRating(book.getId());
    }

    @Override
    public boolean reviewExist(Book book, User user) {
        return this.bookDao.reviewExist(book.getId(), user.getId());
    }

    private void addAvgRating(int bookId) {
        Book book = this.bookService.getBookById(bookId);
        double rating = round(this.bookDao.averageRating(bookId));
        book.setBookRating(rating);
        this.bookService.updateBook(book);
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 1);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
