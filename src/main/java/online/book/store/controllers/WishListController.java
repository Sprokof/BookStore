package online.book.store.controllers;

import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.WishList;
import online.book.store.service.BookService;
import online.book.store.service.CartService;
import online.book.store.service.UserService;
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
    HttpSession session;

    @Autowired
    BookService bookService;

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @ModelAttribute("wishlist")
    public WishList getWishList(){
        return ((User) session.getAttribute("user")).getWishList();
    }

    @GetMapping("/home/wishlist")
    public String wishlist(){
        return "wishlist";

    }

    @PostMapping("/home/wishlist/addtocart")
    public String addToCart(@RequestParam("isbn") String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        User currentUser = (User) session.getAttribute("user");
        cartService.addBookToCart(currentUser, book);
        cartService.updateCart(currentUser.getCart());
        return "wishlist";
    }

    @PostMapping("/home/wishlist/remove")
    public String removeFromWishlist(@RequestParam("isbn") String isbn){
        Book book = bookService.getBookByIsbn(isbn);
        User currentUser = (User) session.getAttribute("user");
        currentUser.getWishList().removeBook(book);
        userService.updateUser(currentUser);
        return "wishlist";
    }


}
