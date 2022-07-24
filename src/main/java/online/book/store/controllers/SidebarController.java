package online.book.store.controllers;

import online.book.store.engines.CategoryQuery;
import online.book.store.engines.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SidebarController {

    @Autowired
    SearchEngine searchEngine;

    @PostMapping("/home/search")
    public String search(@RequestBody CategoryQuery categoryQuery, Model model){
        model.addAttribute("books", searchEngine.getBooksByQuery(categoryQuery));
        return "searchedBooks";
    }
}
