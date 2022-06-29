package online.book.store.controllers;

import online.book.store.engines.SearchQuery;
import online.book.store.engines.SiteEngine;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.engines.SearchEngine;
import online.book.store.service.BookService;
import online.book.store.service.CartService;
import online.book.store.service.UserService;
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
import java.util.stream.Collectors;

@Controller
public class HomeController {


    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private @Qualifier("searchEngine")
    SiteEngine searchEngine;


    @Autowired
    HttpSession session;

    @ModelAttribute("books")
    public List<Book> popularBooks(){
        return bookService.getPopularBooks();
    }

    @ModelAttribute("query")
    public SearchQuery query(){
        return new SearchQuery();
    }


    @GetMapping("/home")
    public String home(Model model) {
        return "home";

    }

    @PostMapping("/home/wishilst/addbooktowishlist")
    public String addToWishList(@RequestParam("isbn") String isbn) {
        Book book = null;
        User currentUser = (User) session.getAttribute("user");
        if (currentUser != null) {
            book = bookService.getBookByIsbn(isbn);
        }
        userService.addBookToWishList(currentUser, book);

        return "home";
    }

    @PostMapping("/home/cart/addbooktocart")
    public String addBookToCart(@RequestParam("isbn") String isbn){
        Book book = bookService.getBookByIsbn(isbn);
        User currentUser = (User) session.getAttribute("user");
        cartService.addBookToCart(currentUser, book);
        userService.updateUser(currentUser);
        return "home";
    }


    @PostMapping("/home/book/search")
    public String search(@RequestParam("text") String text, Model model){
    List<Book> books = ((SearchEngine) searchEngine).getBooksByText(text, bookService);
    model.addAttribute("books", books);
    return "searchresult";

    }

    @GetMapping("/home/book/info")
    public String info(@RequestParam("isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);

        return "info";
    }

}


