package online.book.store.dao;

import online.book.store.entity.BookReview;

import java.util.List;

public interface ReviewsDao {
    int bookReviewCount(String isbn);
    List<BookReview> getBookReviews(String isbn);
}
