package online.book.store.service;


import online.book.store.dao.BookDao;
import online.book.store.engines.SearchResult;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.enums.RotationPriority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class BookServiceImpl implements BookService{

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
    public List<SearchResult> findBooksByParam(String param){
        List<Book> books;
        if(!(books = this.bookDao.getBooksByISBN(param)).isEmpty()){
            return mapToSearchResult(books, RotationPriority.A);
        }

        if(!(books = this.bookDao.getBooksByTitle(param)).isEmpty()){
            return mapToSearchResult(books, RotationPriority.B);
        }

        if(!(books = this.bookDao.getBooksByAuthors(param)).isEmpty()){
            return mapToSearchResult(books, RotationPriority.C);
        }

        if(!(books = this.bookDao.getBooksByDescription(param)).isEmpty()){
            return mapToSearchResult(books, RotationPriority.D);
        }

        if(!(books = this.bookDao.getBooksBySubject(param)).isEmpty()){
            return mapToSearchResult(books, RotationPriority.E);
        }

        if(!(books = this.bookDao.getBooksPublisher(param)).isEmpty()){
            return mapToSearchResult(books, RotationPriority.F);
        }


        return new LinkedList<>();
    }

    private List<SearchResult> mapToSearchResult(List<Book> books, RotationPriority priority){
        return books.stream().map((book) ->
                new SearchResult(book, priority)).
                collect(Collectors.toList());
    }
}
