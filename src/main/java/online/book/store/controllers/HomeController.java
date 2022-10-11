package online.book.store.controllers;


import online.book.store.entity.Book;import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @Autowired
    UserService userService;


    @ModelAttribute("books")
    public List<Book> books() {
        List<Book> books;
        if((books = bookService.getPopularBooks()).isEmpty()){
            books = bookService.getAllBooks();
        }
    return books;
    }


    @GetMapping(value = {"/", "/home"})
    @SuppressWarnings("unchecked")
    public String home(Model model) {
        List<Book> books = (List<Book>) model.getAttribute("books");
        if(books == null || books.isEmpty()){
            return "noresult";
        }
        return "home";
    }

}


