package online.book.store.service;

import lombok.Getter;
import lombok.Setter;
import online.book.store.dao.BookDao;
import online.book.store.engines.SortEngine;
import online.book.store.entity.Book;
import online.book.store.engines.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceImpl implements BookService{


    @Autowired
    private BookDao bookDao;

    @Autowired
    SearchEngine searchEngine;

    @Autowired
    SortEngine sortEngine;

    @Getter
    @Setter
    private List<Book> allBook;

    @Override
    public List<Book> getPopularBooks() {
        return this.bookDao.getPopularBooks();
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookDao.getAllBooks();
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
}
