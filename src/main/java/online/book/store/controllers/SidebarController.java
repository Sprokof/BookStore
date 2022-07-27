package online.book.store.controllers;

import online.book.store.engines.CategoryQuery;
import online.book.store.engines.SearchEngine;
import online.book.store.entity.Category;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class SidebarController {

    @Autowired
    SearchEngine searchEngine;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/instance/popular/categories")
    public ResponseEntity<?> instancePopularCategories(){
        List<Category> categories = categoryService.popularCategories();
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/home/search")
    public String search(@RequestBody CategoryQuery categoryQuery, Model model){
        model.addAttribute("books", searchEngine.getBooksByQuery(categoryQuery));
        return "searchedBooks";
    }


}
