package online.book.store.controllers;

import online.book.store.engines.SearchEngine;
import online.book.store.engines.SortEngine;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SidebarController {

    @Autowired
    private BookService bookService;


    @GetMapping("/home/search/")
    public String getBookByCategory(@RequestParam("category") String category, Model model){
        List<Book> books = bookService.getBooksByCategory(category);
        model.addAttribute("books", books);
        return "booksByCategory";

    }
}
