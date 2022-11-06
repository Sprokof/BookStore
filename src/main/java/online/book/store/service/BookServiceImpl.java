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
        List<Book> books;
        if((books = this.bookDao.getPopularBooks()).isEmpty())
            books = this.bookDao.getAllBooks();

        for(Book book : books){
            String description = book.getDescription();
            String trimDesc = (description.substring(0, lastSubstrIndex(description)) + "...");
            addBookRating(book);
            book.setDescription(trimDesc);
        }
        return books;
    }


    @Override
    public Book getBookByIsbn(String isbn) {
        Book book = this.bookDao.getBookByIsbn(isbn);
        addBookRating(book);
        return book;
    }

    @Override
    public Book getBookById(int id) {
        return this.bookDao.getBookById(id);
    }

    @Override
    public void saveBook(Book book) {
        this.bookDao.saveBook(book);
        updateBooksCategories(book);
    }


    @Override
    public Book getBookByTitle(String title) {
        return this.bookDao.getBookByTitle(title);
    }

    @Override
    public void addBookRating(Book book) {
        book.setBookRating(round(this.bookDao.averageRating(book.getId())));
    }

    private double round(double value) {
        long factor = (long) Math.pow(10, 1);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private int lastSubstrIndex(String description){
        int length = description.length();
        return Math.min(length, 210);

    }

    @Override
    public void updateBooksCategories(Book book) {
        int bookId = getBookByIsbn(book.getIsbn()).getId();
        int categoryId;
        for (Category category : book.getCategories()) {
            categoryId = category.getId();
            this.bookDao.insertBookAndCategories(bookId, categoryId);
        }
    }

    @Override
    public void updateBook(Book book) {
        this.bookDao.updateBook(book);
    }
}
