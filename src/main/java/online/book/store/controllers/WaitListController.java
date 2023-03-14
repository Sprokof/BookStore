package online.book.store.controllers;

import online.book.store.dto.WaitListDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.WaitList;
import online.book.store.service.BookService;
import online.book.store.service.SessionService;
import online.book.store.service.WaitListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller public class WaitListController {

    @Autowired
    private WaitListService waitListService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/waitlist/add")
    public ResponseEntity<HttpStatus> addToWaitList(@RequestBody WaitListDto waitListDto){
        String isbn = waitListDto.getIsbn();
        String sessionid = waitListDto.getSessionid();
        System.out.println(sessionid);
        Book book = this.bookService.getBookByIsbn(isbn);
        WaitList waitList = this.sessionService.getCurrentUser(sessionid, false).getWaitList();
        System.out.println(waitList == null);
        this.waitListService.addToWaitList(book, waitList);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/waitlist/contains")
    public ResponseEntity<WaitListDto> contains(@RequestHeader("session") String sessionid, @RequestParam("isbn") String isbn){
        WaitList waitList = this.sessionService.getCurrentUser(sessionid, false).getWaitList();
        int bookId = this.bookService.getBookIdByISBN(isbn);
        return ResponseEntity.ok(this.waitListService.contains(bookId, waitList));
    }

}
