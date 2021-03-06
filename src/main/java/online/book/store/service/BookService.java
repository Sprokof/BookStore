package online.book.store.service;

import online.book.store.dto.BookDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;

import java.util.List;


public interface BookService {
    List<Book> getPopularBooks();
    List<Book> getAllBooks();
    List<Book> getBooksByCategory(Category category);
    Book getBookByIsbn(String isbn);
    Book getBookById(int id);
    void saveBook(Book book);
    void addOrRemoveCategory(BookDto book, Category category);
}
