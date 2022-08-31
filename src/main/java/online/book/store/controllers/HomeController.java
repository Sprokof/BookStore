package online.book.store.controllers;

import online.book.store.engines.*;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class HomeController {


    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private WishlistService wishlistService;


    @Autowired
    HttpSession session;

    @Autowired
    UserService userService;

    @Autowired
    private SignInService signInService;

    @ModelAttribute("books")
    public List<Book> popularBooks(){
        return bookService.getPopularBooks();
    }


    @GetMapping(value = {"/", "/home"})
    public String home(HttpServletRequest servletRequest) {
        String ip = signInService.getCurrentIP(servletRequest);
        User currentUser;
        if((currentUser = userService.getUserByIP(ip)) != null && currentUser.isRemembered()){
            userService.updateUserInSession(currentUser);
        }
        return "home";
    }

    @PostMapping("/home/wishlist/add")
    public ResponseEntity<?> addToWishList(@RequestParam("title") String title) {
        Book book = bookService.getBookByTitle(title);
        wishlistService.addBookToWishlist(book);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/home/cart/add")
    public ResponseEntity<?> addBookToCart(@RequestParam("title") String title){
        Book book = bookService.getBookByTitle(title);
        cartService.addBookToCart(book);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);

    }


}


