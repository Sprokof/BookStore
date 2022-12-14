package online.book.store.service;

import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.BookReview;
import online.book.store.entity.User;

import java.util.List;

public interface BookReviewService {
    void addReview(BookReviewDto bookReviewDto, User user);
    BookReviewDto reviewExist(Book book, User user);

}
