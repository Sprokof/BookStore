package online.book.store.controllers;

import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.service.BookReviewService;
import online.book.store.service.BookService;
import online.book.store.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ReviewController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private BookService bookService;

    @PostMapping("/reviews/post")
    public ResponseEntity<Integer> postReview (@RequestBody BookReviewDto bookReviewDto){
        String sessionid = bookReviewDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid);
        this.bookReviewService.addReview(bookReviewDto, user);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/reviews/exist")
    public ResponseEntity<String> reviewsExist(@RequestBody BookReviewDto bookReviewDto){
        String sessionid = bookReviewDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid);
        String isbn = bookReviewDto.getIsbn();
        Book book = this.bookService.getBookByIsbn(isbn);
        boolean exist = this.bookReviewService.reviewExist(book, user);
        return ResponseEntity.ok(String.valueOf(exist));
    }
}
