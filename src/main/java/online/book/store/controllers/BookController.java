package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.dto.CategoryDto;
import online.book.store.engines.*;
import online.book.store.entity.Book;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import online.book.store.service.NoticeService;
import online.book.store.service.SignService;
import online.book.store.validation.AbstractValidation;
import online.book.store.validation.BookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@Scope("request")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SignService signService;

    @Autowired
    private NoticeService noticeService;


    private AbstractValidation bookValidation;


    @ModelAttribute("categories")
    public List<CategoryDto> categories() {
        return categoryService.getAllCategories();
    }


    @GetMapping("/book")
    public String book(@RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "isbn", required = false) String isbn, Model model) {
        Book book = bookService.getBookByParams(title, isbn);
        model.addAttribute("book", book);
        return "bookInfo";
    }


    @GetMapping("/add/session")
    public String addBook(@RequestParam("id") String sessionid) {
        if (!signService.adminsRequest(sessionid)) {
            throw new ResourceNotFoundException();
        }
        return "addBook";
    }

    @PostMapping("/book/add")
    public ResponseEntity<Map<String, String>> addBook(@RequestBody BookDto bookDto) {
        this.bookValidation = new BookValidation.AddBookValidation(this.bookService);
        bookValidation.validation(bookDto);
        if (!bookValidation.hasErrors()) {
            Book book = bookDto.doBookBuilder();
            bookService.saveBook(book);
        }
        return ResponseEntity.ok(bookValidation.validationErrors());
    }

    @GetMapping("/update/session")
    public String updateBook(@RequestParam ("id") String sessionid) {
        if (!signService.adminsRequest(sessionid)) {
            throw new ResourceNotFoundException();
        }
        return "updateBook";
    }


    @PostMapping("/book/update")
    public ResponseEntity<Map<String, String>> updateBook(@RequestBody BookDto bookDto){
        this.bookValidation = new BookValidation.UpdateBookValidation(this.bookService);
        bookValidation.validation(bookDto);
        if (!bookValidation.hasErrors()) {
            Book book;
            if(!(book = bookService.updateBook(bookDto)).available()){
                noticeService.createAvailableNotice(book);
            }
        }
        return ResponseEntity.ok(bookValidation.validationErrors());
    }


    @GetMapping("/books/search")
    public String booksList(@RequestParam Map<String, String> params, Model model) {
        SearchQuery query = SearchQuery.init(params, this.bookService,
                this.categoryService).execute();
        boolean hasResults = query.hasResults();
        if (!hasResults) return "result";
        else {
            model.addAttribute("page", query.getPage());
            return "books";
        }
    }

}


