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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    @GetMapping("/home/wishlist")
    public String wishlist(@RequestParam("login") String login, Model model){
       User user = userService.getUserByLogin(login);
       List<Book> wishlistsBooks = user.getWishList().getBooks();
       model.addAttribute("rows", siteEngine.mapBooksToRow(wishlistsBooks));
       return "wishlist";
    }

    @PostMapping("/home/wishlist/remove")
    public ResponseEntity<Integer> removeFromWishlist(@RequestBody WishlistDto wishlistDto, HttpServletRequest request){
        String isbn = wishlistDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        String sessionid = wishlistDto.getSessionid();
        Wishlist userWishlist = sessionService.getCurrentUser(sessionid).getWishList();
        wishlistService.removeFromWishlist(book, userWishlist);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/home/wishlist/add")
    public ResponseEntity<Integer> addToWishList(@RequestBody WishlistDto wishlistDto) {
        String isbn = wishlistDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        String sessionid = wishlistDto.getSessionid();
        Wishlist userWishlist = sessionService.getCurrentUser(sessionid).getWishList();
        wishlistService.addBookToWishlist(book, userWishlist);
        return ResponseEntity.ok(200);
    }


    @PostMapping("/home/wishlist/contains")
    public ResponseEntity<WishlistDto> contains(@RequestBody WishlistDto wishlistDto){
        String isbn = wishlistDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        String sessionid = wishlistDto.getSessionid();
        Wishlist userWishlist = sessionService.getCurrentUser(sessionid).getWishList();
        wishlistService.contains(book, userWishlist);
        return ResponseEntity.ok(wishlistService.contains(book, userWishlist));
    }


}


