package online.book.store.service;

import online.book.store.dto.BookDto;
import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.entity.User;

import java.util.List;


public interface BookService {
    List<Book> getPopularBooks();
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book getBookByTitle(String title);
    Book getBookById(int id);
    void saveBook(Book book);
    void addBookRating(Book book);
    void updateBooksCategories(Book book);
    void updateBook(Book book);

}
