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


    @GetMapping(value = {"/", "/home"})
    public String home(Model model) {
        List<Book> books = bookService.getPopularBooks();
        if(books == null || books.isEmpty()){
            return "result";
        }
        model.addAttribute("books", books);
        return "home";
    }

}


