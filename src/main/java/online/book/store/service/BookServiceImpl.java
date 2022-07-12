package online.book.store.service;

import lombok.Getter;
import lombok.Setter;
import online.book.store.dao.BookDao;
import online.book.store.engines.SortEngine;
import online.book.store.engines.SortTypes;
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

    @Autowired
    SortEngine engine;

    @Override
    public List<Book> getPopularBooks() {
        engine.setBookListToSort(bookDao.getPopularBooks());
        return engine.getSortBooks(SortTypes.DEFAULT);
    }

    @Override
    public List<Book> getAllBooks() {
        engine.setBookListToSort(bookDao.getPopularBooks());
        return engine.getSortBooks(SortTypes.DEFAULT);
    }

    @Override
    public List<Book> getBooksByCategory(String category) {
        engine.setBookListToSort(bookDao.getPopularBooks());
        return engine.getSortBooks(SortTypes.DEFAULT);
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
