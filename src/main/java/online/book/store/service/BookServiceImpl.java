package online.book.store.service;


import online.book.store.dao.BookDao;
import online.book.store.dto.BookDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getPopularBooks() {
        return this.bookDao.getPopularBooks();
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = this.bookDao.getAllBooks();
        for(Book book : books){
            String trimDesc = (book.getDescription().substring(0, 205) + "...");
            book.setDescription(trimDesc);
        }
        return books;
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        return this.bookDao.getBooksByCategory(category);
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return this.bookDao.getBookByIsbn(isbn);
    }

    @Override
    public Book getBookById(int id) {
        return this.bookDao.getBookById(id);
    }

    @Override
    public void saveBook(Book book) {
        this.bookDao.saveBook(book);
    }


    @Override
    public Book getBookByTitle(String title) {
        return this.bookDao.getBookByTitle(title);
    }

    @Override
    public double averageRating(Integer bookId) {
        return round(this.bookDao.averageRating(bookId));
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 1);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
