package online.book.store.service;

import lombok.Getter;
import lombok.Setter;
import online.book.store.dao.BookDao;
import online.book.store.dto.BookDto;
import online.book.store.engines.SortEngine;
import online.book.store.engines.SortTypes;
import online.book.store.entity.Book;
import online.book.store.engines.SearchEngine;
import online.book.store.entity.Category;
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
        return engine.getSortBooks();
    }

    @Override
    public List<Book> getAllBooks() {
        engine.setBookListToSort(bookDao.getAllBooks());
        return engine.getSortBooks();
    }

    @Override
    public List<Book> getBooksByCategory(Category category) {
        engine.setBookListToSort(bookDao.getBooksByCategory(category.getCategory()));
        return engine.getSortBooks();
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
}
