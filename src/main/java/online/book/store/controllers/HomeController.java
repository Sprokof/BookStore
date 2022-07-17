package online.book.store.controllers;

import online.book.store.engines.*;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
        session.setAttribute("booksCategories", categoryService.allCategory());
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


    @GetMapping("/home/book/info")
    public String info(@RequestParam("isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);

        return "bookInfo";
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

    @GetMapping("/home/search")
    public String search(@RequestParam("query") String text, Model model){
        List<Book> books = searchEngine.getBooksByText(new SearchQuery(text));
        model.addAttribute("books", books);
        return "searchedBooks";
    }



}


