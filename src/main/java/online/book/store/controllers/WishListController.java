package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.Wishlist;
import online.book.store.service.BookService;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import online.book.store.service.WishlistService;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WishListController {

    @Autowired
    BookService bookService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    UserService userService;

    @Autowired
    private SignInService signInService;


    @GetMapping("/home/wishlist")
    public String wishlist(HttpServletRequest request, Model model){
       User user = signInService.getUserFromRequest(request);
       Wishlist wishlist = user.getWishList();
       model.addAttribute("wishlist", wishlist);
       return "wishlist";
    }

    @PostMapping("/home/wishlist/remove")
    public ResponseEntity<?> removeFromWishlist(@RequestBody String isbn){
        Book book = bookService.getBookByIsbn(isbn);
        Wishlist userWishlist = signInService.getSavedUser().getWishList();
        wishlistService.removeFromWishlist(book, userWishlist);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/home/wishlist/add")
    public ResponseEntity<Integer> addToWishList(@RequestBody String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        Wishlist userWishlist = signInService.getSavedUser().getWishList();
        wishlistService.addBookToWishlist(book, userWishlist);
        return ResponseEntity.ok(200);
    }


    @GetMapping("/home/wishlist/contains")
    public ResponseEntity<String> contains(@RequestBody String isbn){
        Book book = bookService.getBookByIsbn(isbn);
        Wishlist userWishlist = signInService.getSavedUser().getWishList();
        String contains = String.valueOf(wishlistService.contains(book, userWishlist));
        return ResponseEntity.ok(contains);
    }

}
