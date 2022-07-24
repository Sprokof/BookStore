package online.book.store.engines;

import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class SearchEngine{

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    SortEngine sortEngine = SortEngine.instanceSortEngine();

    public  List<Book> getBooksByQuery(SearchQuery query) {
        if (query instanceof CategoryQuery) {
            Category searchCategory = ((CategoryQuery) query).getCategory();
            List<Book> booksByCategory = bookService.getBooksByCategory(searchCategory);
            return sortEngine.setBookListToSort(booksByCategory).getSortBooks();
        }

        else {
            TextQuery textQuery = (TextQuery) query;
            List<Book> books = this.bookService.getAllBooks();
            List<Book> resultedBooks = new LinkedList<>();
            String[] booksFields = books.toString().split("\\,");

            int index = 0;

            while (index != books.size()) {
                for (String field : booksFields) {
                    if (contains(textQuery.getQueryText(), field)) {
                        resultedBooks.add(books.get(index));
                    }
                }
                index++;
            }
            return sortEngine.setBookListToSort(resultedBooks).getSortBooks();
        }
    }

    private boolean contains(String text, String field){
        int cns = ((field.length() / 2) + 1), countCns = 0;
        String[] letters = text.split("");
        String[] fieldContent = field.split("");

        for (String letter : letters) {
            for (String l : fieldContent) {
                if (l.equals(letter)) {
                    if ((countCns++) == cns) {
                        return true;
                    }
                }
            }
        }
    return false;
    }


}
