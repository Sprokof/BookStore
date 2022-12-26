package online.book.store.controllers;

import online.book.store.dto.WishlistDto;
import online.book.store.engines.SiteEngine;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.Wishlist;
import online.book.store.service.BookService;
import online.book.store.service.SessionService;
import online.book.store.service.UserService;
import online.book.store.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class WishListController {

    @Autowired
    BookService bookService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    UserService userService;

    @Autowired
    private SiteEngine siteEngine;

    @Autowired
    private SessionService sessionService;


    @GetMapping("/wishlist")
    public String wishlist(@RequestParam("user") String login, Model model){
       User user = userService.getUserByLogin(login);
       Wishlist wishlist = user.getWishList();
       if(wishlist.isEmpty()){
           return "result";
       }
       List<Book> wishlistsBooks = user.getWishList().getBooks();
       model.addAttribute("rows", siteEngine.mapBooksToRow(wishlistsBooks));
       return "wishlist";
    }

    @DeleteMapping("/wishlist/item/remove")
    public ResponseEntity<HttpStatus> removeFromWishlist(@RequestBody WishlistDto wishlistDto){
        String isbn = wishlistDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        String sessionid = wishlistDto.getSessionid();
        Wishlist userWishlist = sessionService.getCurrentUser(sessionid).getWishList();
        HttpStatus status = wishlistService.removeFromWishlist(book, userWishlist);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/wishlist/item/add")
    public ResponseEntity<HttpStatus> addToWishList(@RequestBody WishlistDto wishlistDto) {
        String isbn = wishlistDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        String sessionid = wishlistDto.getSessionid();
        Wishlist userWishlist = sessionService.getCurrentUser(sessionid).getWishList();
        wishlistService.addBookToWishlist(book, userWishlist);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @GetMapping("/wishlist/item/contains")
    public ResponseEntity<WishlistDto> contains(@RequestHeader("session") String sessionid, @RequestParam("isbn") String isbn){
        Wishlist userWishlist = sessionService.getCurrentUser(sessionid).getWishList();
        return ResponseEntity.ok(wishlistService.contains(isbn, userWishlist));
    }




}


