package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("bookCategories")
    public List<Category> getCategories(){
        return categoryService.allCategories();
    }

    @Autowired
    private @Qualifier("bookValidation") Validator bookValidator;

    @GetMapping("/home/book/info")
    public String info(@RequestParam("isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);

        return "bookInfo";
    }


    @GetMapping("/home/book/add")
    public String addBook(Model model){
        model.addAttribute("book", new BookDto());
        return "addBook";
    }

    @PostMapping("/home/book/add")
    public String addBook(@Valid BookDto bookDto,
                          BindingResult bindingResult, Model model){

        bookValidator.validate(bookDto, bindingResult);
        if(bindingResult.hasErrors()) {
            return "addBook";
        }
        bookService.saveBook(bookDto.doBookBuilder());
        model.addAttribute("book", bookDto);

        return "addBook";
    }
}
