package online.book.store.engines;

import online.book.store.entity.Book;
import online.book.store.service.BookService;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;


@Component
public class SearchEngine implements SiteEngine{

    public  List<Book> getBooksByText(String text, BookService bookService){
        List<Book> books = bookService.getAllBooks();
        List<Book> resultedBooks = new LinkedList<>();
        String[] booksFields = books.toString().split(",");

        int index = 0;

        while(index != books.size()) {
                for(String field : booksFields){
                if (contains(text, field)){
                    resultedBooks.add(books.get(0));
                }
            }
        }
    return resultedBooks;
    }

    private boolean contains(String text, String field){
        int cns = ((field.length() / 2) + 1), countCns = 0;
        String[] letters = text.split("");
        String[] fieldContent = field.split("");

    for(int i = 0; i < letters.length; i ++){
        for(String l : fieldContent){
            if(l.equals(letters[i])) {
                if ((countCns++) == cns) {
                    return true;
                }
            }
            }
        }
    return false;
    }
}
