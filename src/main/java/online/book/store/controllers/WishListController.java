package online.book.store.controllers;

import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.Wishlist;
import online.book.store.service.BookService;
import online.book.store.service.UserService;
import online.book.store.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class WishListController {

    @Autowired
    BookService bookService;

    @Autowired
    WishlistService wishlistService;

    @Autowired
    UserService userService;


    @ModelAttribute("wishlist")
    public Wishlist getWishList(){
        return userService.getCurrentUser().getWishList();
    }

    @GetMapping("/home/wishlist")
    public String wishlist(){
        return "wishlist";

    }

    @PostMapping("/home/wishlist/addtocart")
    public String addToCart(@RequestParam("isbn") String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        wishlistService.addToCart(book);
        return "wishlist";
    }

    @PostMapping("/home/wishlist/remove")
    public String removeFromWishlist(@RequestParam("isbn") String isbn){
        Book book = bookService.getBookByIsbn(isbn);
        wishlistService.removeFromWishlist(book);
        return "wishlist";
    }


}
