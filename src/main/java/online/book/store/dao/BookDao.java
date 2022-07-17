package online.book.store.dao;

import online.book.store.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> getPopularBooks();
    List<Book> getAllBooks();
    List<Book> getBooksByCategory(String category);
    Book getBookByIsbn(String isbn);
    Book getBookById(int id);
    void saveBook(Book book);

}
