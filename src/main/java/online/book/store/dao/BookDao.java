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
    void updateBook(Book book);
    boolean reviewExist(int bookId, int userId);
    boolean bookExist(String isbn);
    int getBookIdByISBN(String isbn);
    List<Book> getBooksByTitle(String title);
    List<Book> getBooksByISBN(String isbn);
    List<Book> getBooksByAuthors(String authors);
    List<Book> getBooksByDescription(String description);
    List<Book> getBooksPublisher(String publisher);
    List<Book> getBooksBySubject(String subject);


}
