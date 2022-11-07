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
    }

    @Override
    public boolean reviewExist(Book book, User user) {
        return this.bookDao.reviewExist(book.getId(), user.getId());
    }
}
