package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.dto.CategoryDto;
import online.book.store.engines.*;
import online.book.store.entity.Book;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import online.book.store.service.SignInService;
import online.book.store.validation.AbstractValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Qualifier("bookValidation")
    private AbstractValidation bookValidation;


    @ModelAttribute("categories")
    public List<CategoryDto> categories(){
        return categoryService.getAllCategories();
    }


    @GetMapping("/home/book")
    public String info(@RequestParam("isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        double rating = bookService.averageRating(book.getId());
        book.setBookRating(rating);
        model.addAttribute("book", book);
        return "bookInfo";
    }


    @GetMapping("/home/book/add")
    public String addBook(@RequestParam("sessionid") String sessionid){
        if(!signInService.adminsRequest(sessionid)){
            throw new ResourceNotFoundException();
        }
        return "addBook";
    }

    @PostMapping("/home/book/add")
    public ResponseEntity<Map<String, String>> addBook(@RequestBody BookDto bookDto){

        bookValidation.validation(bookDto);
        if(!bookValidation.hasErrors()){
            Book book = bookDto.doBookBuilder();
            bookService.saveBook(book);
        }
        return ResponseEntity.ok(bookValidation.validationErrors());
    }




    @GetMapping ("/home/books/search")
    public String booksList (@RequestParam Map<String, String> params, Model model){
        SearchQuery searchQuery = new SearchQuery(params.get("query"));
        SortTypes sortType = SortTypes.getTypeByName(params.get("type"));
        boolean hasResult = this.engine.executeSearchQuery(searchQuery, sortType).hasResult();
        if(!hasResult){
            return "result";
        }
        else {
            List<SiteEngine.Row> resultRows = this.engine.mapResultToRow();
            model.addAttribute("rows", resultRows);
            return "books";
        }
    }


    @GetMapping("/search/params")
    public ResponseEntity<SearchParam> searchParams (){
        SearchParam searchParam = this.engine.getSearchParam();
        return ResponseEntity.ok(searchParam);
    }



}
