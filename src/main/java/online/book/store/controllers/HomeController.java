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
        User currentUser;
        if((currentUser = signInService.getCurrentUser(servletRequest))
                != null && currentUser.isRemembered()){
            userService.updateUserInSession(currentUser);
        }
        return "home";
    }

}


