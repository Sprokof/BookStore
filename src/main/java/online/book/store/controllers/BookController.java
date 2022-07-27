package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.dto.CategoryDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private @Qualifier("bookValidation") Validator bookValidator;

    @GetMapping("/home/book/info")
    public String info(@RequestParam("isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("book", book);

        return "bookInfo";
    }

    @ModelAttribute("categories")
    public List<CategoryDto> categories(){
        return categoryService.allCategories().stream().
                map((c) -> new CategoryDto(c.getCategory())).
                collect(Collectors.toList());
    }


    @ModelAttribute("book")
    public BookDto book(){
        return new BookDto();
    }

    @GetMapping("/home/book/add")
    public String addBook(Model model){
        model.addAttribute("book", new BookDto());
        return "addBook";
    }

    @PostMapping("/home/book/add")
    @SuppressWarnings("unchecked")
    public String addBook( @ModelAttribute("book") @Valid BookDto bookDto, Model model,
                          BindingResult bindingResult){

        bookValidator.validate(bookDto, bindingResult);
        if(bindingResult.hasErrors()) {
            return "addBook";
        }
        bookService.saveBook(bookDto.doBookBuilder());
        return "addBook";
    }


    @PostMapping("/home/book/add/category")
    public ResponseEntity.BodyBuilder addCategory(@RequestBody List<Category> categories, Model model){
        BookDto currentBook = ((BookDto) model.getAttribute("book"));
        currentBook.setBooksCategory(categories);
        return ResponseEntity.status(200);
    }

    @GetMapping("/instance/all/categories")
    public ResponseEntity<?> instanceCategories(){
        List<Category> categories = categoryService.allCategories();
        return ResponseEntity.ok(categories);
    }

}
