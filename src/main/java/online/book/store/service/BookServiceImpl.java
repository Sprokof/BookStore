package online.book.store.service;


import online.book.store.dao.BookDao;
import online.book.store.dto.BookDto;
import online.book.store.entity.Book;
import online.book.store.engines.SiteEngine;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceImpl implements BookService{

    @Autowired
    private BookDao bookDao;

    @Autowired
    SiteEngine siteEngine;


    @Override
    public List<Book> getPopularBooks() {
        return this.bookDao.getPopularBooks();
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookDao.getAllBooks();
    }

    @Override
    public List<Book> getBooksByCategory(Category category) {
        return this.bookDao.getBooksByCategory(category.getCategory());
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
    public void addOrRemoveCategory(BookDto book, Category category) {
        List<Category> categories;
            if((categories = book.getBooksCategory()).contains(category)){
                categories.remove(category);
            }
            else categories.add(category);
    }

    @Override
    public Book getBookByTitle(String title) {
        return this.bookDao.getBookByTitle(title);
    }

    @Override
    public double averageRating(Book book) {
        return this.bookDao.averageRating(book);
    }
}
