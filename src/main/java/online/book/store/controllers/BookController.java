package online.book.store.controllers;

import online.book.store.dto.BookDto;
import online.book.store.entity.Book;
import online.book.store.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;


    @Autowired
    private @Qualifier("bookValidation") Validator bookValidator;

    @GetMapping("/home/book/info")
    public String bookInfo(@RequestParam("isbn") String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        model.addAttribute("currentBook", book);

    return "bookInfo";
    }

    @ModelAttribute("addBook")
    public BookDto bookDto(){
        BookDto bookDto = new BookDto();
        bookDto.setBookImage(null);
        return bookDto;
    }

    @GetMapping("/home/book/add")
    public String addBook(){
        return "addBook";
    }

    @PostMapping("/home/book/add")
    public String addBook(@ModelAttribute("addBook") @Valid BookDto bookDto,
                          BindingResult bindingResult){

        bookValidator.validate(bookDto, bindingResult);
        if(bindingResult.hasErrors()) {
            return "addBook";
        }

        bookDto.setAvailable(BookDto.AVAILABLE_STATUS[0]);
        bookService.saveBook(bookDto.doBookBuilder());

    return "addBook";
    }



}
