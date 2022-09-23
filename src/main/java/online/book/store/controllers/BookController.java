package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.dto.CategoryDto;
import online.book.store.engines.*;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import online.book.store.service.SignInService;
import online.book.store.validation.AbstractValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private SiteEngine engine;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SignInService signInService;


    @Autowired
    private @Qualifier("bookValidation")
    AbstractValidation bookValidation;

    @ModelAttribute("categories")
    public List<CategoryDto> categories(){
        return categoryService.popularCategories();
    }


    @GetMapping("/home/book")
    public String info(@RequestParam("title") String title, Model model){
        Book book = bookService.getBookByTitle(title);
        double rating = bookService.averageRating(book.getId());
        book.setBookRating(rating);
        model.addAttribute("book", book);
        return "bookInfo";
    }


    @GetMapping("/home/book/add")
    public String addBook(Model model, HttpServletRequest request){
        if(!signInService.adminsRequest(request)){
            throw new ResourceNotFoundException();
        }
        model.addAttribute("book", new BookDto());
        return "addBook";
    }

    @PostMapping("/home/book/add")
    @SuppressWarnings("unchecked")
    public String addBook(BookDto bookDto){
        bookValidation.validation(bookDto);
        if(!bookValidation.hasErrors()){
            bookService.saveBook(bookDto.doBookBuilder());
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


    @GetMapping ("/home/books/search/")
    public String booksList (@RequestParam Map<String, String> params, Model model){
        SearchQuery searchQuery = new SearchQuery(params.get("query"));
        SortConfig config = new SortConfig(SortType.getTypeByName(params.get("type")));
        boolean hasResult = this.engine.executeSearchQuery(searchQuery, config).hasResult();
        if(!hasResult){
            return "noresult";
        }
        else {
            List<SiteEngine.Row> resultRows = this.engine.mapResultToRow();
            model.addAttribute("rows", resultRows);
            return "books";
        }
    }


    @GetMapping("/last/query/param")
    public ResponseEntity<String> urlParams (){
        String value = this.engine.getLastQueryValue();
        return ResponseEntity.ok(value);
    }



}
