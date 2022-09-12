package online.book.store.controllers;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
       User user = signInService.getCurrentUser(request);
       Wishlist wishlist = user.getWishList();
       model.addAttribute("wishlist", wishlist);
       return "wishlist";
    }

    @PostMapping("/home/wishlist/remove")
    public ResponseEntity<?> removeFromWishlist(@RequestParam("title") String title){
        Book book = bookService.getBookByTitle(title);
        wishlistService.removeFromWishlist(book, signInService.getSavedUser().getWishList());
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @PostMapping("/home/wishlist/add")
    public ResponseEntity<?> addToWishList(@RequestParam("title") String title) {
        Book book = bookService.getBookByTitle(title);
        wishlistService.addBookToWishlist(book, signInService.getSavedUser().getWishList());
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}
