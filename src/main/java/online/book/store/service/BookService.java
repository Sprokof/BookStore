package online.book.store.service;

import online.book.store.entity.Book;

import java.util.List;


public interface BookService {
    List<Book> getPopularBooks();
    List<Book> getAllBooks();
    List<Book> getBooksByCategory(String category);
    Book getBookByIsbn(String isbn);
    Book getBookById(int id);
}
