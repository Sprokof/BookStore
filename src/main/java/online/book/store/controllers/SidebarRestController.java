package online.book.store.controllers;

import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SidebarRestController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping ("/home/book/categories")
    public List<Category> category(){
        return categoryService.allCategories();
    }

    @GetMapping("/home/logout")
    public String logout(HttpServletRequest request){
        if(request.getSession().getAttribute("user") != null){
            request.removeAttribute("user");
        }
        return "home";
    }

    @GetMapping("/test")
    public String test(){
        return "navbar";
    }
}
