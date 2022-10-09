package online.book.store.controllers;

import online.book.store.dto.RequestDto;
import online.book.store.dto.WishlistDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.Wishlist;
import online.book.store.service.BookService;
import online.book.store.service.UserService;
import online.book.store.service.WishlistService;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private SessionStorage sessionStorage;



    @GetMapping("/home/wishlist")
    public String wishlist(HttpServletRequest request, Model model){
       User user = sessionStorage.getUser(request);
       Wishlist wishlist = user.getWishList();
       model.addAttribute("wishlist", wishlist);
       return "wishlist";
    }

    @PostMapping("/home/wishlist/remove")
    public ResponseEntity<Integer> removeFromWishlist(@RequestBody String isbn, HttpServletRequest request){
        Book book = bookService.getBookByIsbn(isbn);
        Wishlist userWishlist = sessionStorage.getUser(request).getWishList();
        wishlistService.removeFromWishlist(book, userWishlist);

        return ResponseEntity.ok(200);
    }

    @PostMapping("/home/wishlist/add")
    public ResponseEntity<Integer> addToWishList(@RequestBody String isbn, HttpServletRequest request) {
        Book book = bookService.getBookByIsbn(isbn);
        Wishlist userWishlist = sessionStorage.getUser(request).getWishList();
        wishlistService.addBookToWishlist(book, userWishlist);
        return ResponseEntity.ok(200);
    }


    @PostMapping("/home/wishlist/contains")
    public ResponseEntity<WishlistDto> contains(@RequestBody RequestDto requestDto){
        String login = requestDto.getLogin();
        String isbn = requestDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        Wishlist userWishlist = sessionStorage.getUser(login).getWishList();
        wishlistService.contains(book, userWishlist);
        return ResponseEntity.ok(wishlistService.contains(book, userWishlist));
    }

}
