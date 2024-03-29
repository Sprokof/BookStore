package online.book.store.service;

import online.book.store.dto.BookDto;
import online.book.store.dto.BookReviewDto;
import online.book.store.engines.SearchQuery;
import online.book.store.engines.SearchResult;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.entity.User;
import org.springframework.boot.CommandLineRunner;

import java.util.List;


public interface BookService {
    List<Book> getPopularBooks();
    Book getBookByParams(String title, String isbn);
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book getBookByTitle(String title);
    Book getBookById(int id);
    void saveBook(Book book);
    void updateBooksCategories(Book book);
    void updateBook(Book book);
    boolean bookExist(String isbn);
    List<SearchResult> findBooksBySearchQuery(SearchQuery searchQuery, String[] searchColumns);
    int getBookIdByISBN(String isbn);

    Book updateBook(BookDto bookDto);



}
