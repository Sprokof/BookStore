package online.book.store.service;


import online.book.store.dao.BookDao;
import online.book.store.engines.SearchQuery;
import online.book.store.engines.SearchResult;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.enums.RelevancePriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@Component
public class BookServiceImpl implements BookService{

    private static final String REQUEST_PARAM_WHITESPACE = "%20";

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Book> getPopularBooks() {
        List<Book> books = this.bookDao.getPopularBooks();
        books.forEach(book -> {
            String description = book.getDescription();
            String trimDesc = (description.substring(0, lastSubstrIndex(description)) + "...");
            book.setDescription(trimDesc);
        });
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        return this.bookDao.getAllBooks();

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
    public synchronized void saveBook(Book book) {
        new Thread(() -> {
            this.bookDao.saveBook(book);
            pause();
            updateBooksCategories(book);
        }).start();
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

    private synchronized void pause(){
        try {
            wait(130);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean bookExist(String isbn) {
        return this.bookDao.bookExist(isbn);
    }


    @Override
    public List<SearchResult> findBooksBySearchQuery(SearchQuery searchQuery, String[] searchColumns) {
        List<SearchResult> results = new LinkedList<>();
        String query = searchQuery.getQueryText();
        for (String column : searchColumns) {
            List<Book> books = this.bookDao.findBooksBySearchQuery(query, column);
            if (!books.isEmpty()) {
                RelevancePriority priority = RelevancePriority.valueOfField(column);
                addBooksToResults(books, results, priority);
            }
        }
    return results;
    }

    private void addBooksToResults(List<Book> books, List<SearchResult> results, RelevancePriority priority){
        for (Book book : books) {
            SearchResult result = new SearchResult(book, priority);
            if (!results.contains(result)) results.add(result);
        }
    }

    @Override
    public Book getBookByParams(String param) {
        Book book;
        if((book = this.getBookByIsbn(param)) != null) return book;
        return this.getBookByTitle(param.replaceAll(REQUEST_PARAM_WHITESPACE, " "));
    }
}
