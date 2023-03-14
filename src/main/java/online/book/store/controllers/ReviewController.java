package online.book.store.controllers;

import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.BookReview;
import online.book.store.entity.User;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.BookReviewService;
import online.book.store.service.BookService;
import online.book.store.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class ReviewController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private BookReviewService bookReviewService;

    @Autowired
    private BookService bookService;


    @PostMapping("/reviews/post")
    public ResponseEntity<HttpStatus> postReview (@RequestBody BookReviewDto bookReviewDto){
        String sessionid = bookReviewDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid, true);
        this.bookReviewService.addReview(bookReviewDto, user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @GetMapping("/reviews/exist")
    public ResponseEntity<BookReviewDto> reviewExist(@RequestHeader("session") String sessionid, @RequestParam("isbn") String isbn){
        User user = this.sessionService.getCurrentUser(sessionid, true);
        Book book = this.bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(this.bookReviewService.reviewExist(book, user));
    }


}
