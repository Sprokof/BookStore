package online.book.store.controllers;

import online.book.store.engines.SortEngine;
import online.book.store.entity.Book;
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

    @Autowired
    SortEngine sortEngine;



    @ModelAttribute("sortTypes")
    public String[] sortTypes(){
        return SortEngine.sortTypes();
    }


    private static String bookCategory;

    @GetMapping("/home/book/")
    public String getBookByCategory(@RequestParam("category") String category, Model model){
        if(category.isEmpty()){ category = bookCategory;}
        bookCategory = category;

        List<Book> books = sortEngine.getSortBooks(SortEngine.sortType,
                            bookService.getBooksByCategory(category));

        model.addAttribute("booksByCat", books);

        return "booksbycategories";

    }

    @GetMapping("/home/book/sort")
    public String sortBooks(@RequestParam("type") String type){
        SortEngine.sortType = type;
        return "redirect:/home/book";

    }
}
