package online.book.store.service;


import online.book.store.dao.BookDao;
import online.book.store.dto.BookDto;
import online.book.store.dto.BookReviewDto;
import online.book.store.entity.Book;
import online.book.store.entity.BookReview;
import online.book.store.entity.Category;
import online.book.store.entity.User;
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
            book.setDescription(trimDesc);
        }
        return books;
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
        updateBooksCategories(book);
    }


    @Override
    public Book getBookByTitle(String title) {
        return this.bookDao.getBookByTitle(title);
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
