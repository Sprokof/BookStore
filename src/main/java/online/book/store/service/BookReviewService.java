package online.book.store.service;

import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;

public interface BookReviewService {
    void addReview(BookReviewDto bookReviewDto, User user);
    boolean reviewExist(Book book, User user);
}
