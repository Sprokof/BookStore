package online.book.store.dao;

import online.book.store.entity.Book;

import java.util.List;

public interface BookDao {
    List<Book> getPopularBooks();
    List<Book> getAllBooks();
    Book getBookByIsbn(String isbn);
    Book getBookByTitle(String title);
    Book getBookById(int id);
    void saveBook(Book book);
    double averageRating(Integer bookId);
    void insertBookAndCategories(int bookId, int categoryId);


}
