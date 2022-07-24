package online.book.store.controllers;

import online.book.store.engines.*;
import online.book.store.entity.Book;
import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


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

    SortEngine sortEngine = SortEngine.instanceSortEngine();

    SearchEngine searchEngine;

    @Autowired
    HttpSession session;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("books")
    public List<Book> popularBooks(){
        return bookService.getPopularBooks();
    }


    @GetMapping(value = {"/", "/home"})
    public String home() {
        session.setAttribute("booksCategories", categoryService.allCategories());
        return "home";

    }

    @PostMapping("/home/wishlist/addbooktowishlist")
    public String addToWishList(@RequestParam("isbn") String isbn) {
        wishlistService.addBookToWishlist(bookService.getBookByIsbn(isbn));
        return "home";
    }

    @PostMapping("/home/cart/addbooktocart")
    public String addBookToCart(@RequestParam("isbn") String isbn){
        Book book = bookService.getBookByIsbn(isbn);
        cartService.addBookToCart(book);
        return "home";
    }


    //sort section

    @ModelAttribute("sortConfig")
    public SortConfig sortConfig(){
        return sortEngine.getSortConfig();
    }

    @ModelAttribute("sortTypes")
    public SortTypes[] sortTypes(){
        return SortConfig.sortTypes();
    }

    @PostMapping("/home/search")
    public String search(@RequestBody TextQuery textQuery, Model model){
        model.addAttribute("books", searchEngine.getBooksByQuery(textQuery));
        return "searchedBooks";
    }

}


