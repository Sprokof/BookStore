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
    public void postReview (@RequestBody BookReviewDto bookReviewDto){
        String sessionid = bookReviewDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid);
        this.bookReviewService.addReview(bookReviewDto, user);
    }

    @GetMapping("/reviews/exist")
    public ResponseEntity<String> reviewExist(@RequestHeader("session") String sessionid, @RequestParam("isbn") String isbn){
        User user = this.sessionService.getCurrentUser(sessionid);
        Book book = this.bookService.getBookByIsbn(isbn);
        boolean exist = this.bookReviewService.reviewExist(book, user);
        return ResponseEntity.ok(String.valueOf(exist));
    }

    @GetMapping("/reviews/load")
    public ResponseEntity<List<BookReview>> loadReviews(@RequestParam Map<String, String> params) {
        int index = Integer.parseInt(params.get("index"));
        String isbn = params.get("isbn");
        List<BookReview> loadedReviews = this.bookReviewService.loadReviewsByISBN(isbn, index);
        return ResponseEntity.ok(loadedReviews);
    }

    @GetMapping("/review/has")
    public ResponseEntity<String> hasReviews(@RequestParam Map<String, String> params){
        int index = Integer.parseInt(params.get("index"));
        String isbn = params.get("isbn");
        return ResponseEntity.ok(String.valueOf(this.bookReviewService.hasReviews(isbn, index)));
    }

}
