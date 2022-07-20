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
public class SearchEngine implements SiteEngine{

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    SortEngine sortEngine = SortEngine.instanceSortEngine();

    public  List<Book> getBooksByText(SearchQuery query){
        if(categorySearch(query)){
            return sortEngine.setBookListToSort(bookService.
                    getBooksByCategory(query.getQueryText())).getSortBooks();

        }

        List<Book> books = this.bookService.getAllBooks();
        List<Book> resultedBooks = new LinkedList<>();
        String[] booksFields = books.toString().split("\\,");

        int index = 0;

        while(index != books.size()) {
                for(String field : booksFields){
                if (contains(query, field)){
                    resultedBooks.add(books.get(index));
                }
            }
        }
            return sortEngine.setBookListToSort(resultedBooks).getSortBooks();
    }

    private boolean contains(SearchQuery query, String field){
        int cns = ((field.length() / 2) + 1), countCns = 0;
        String[] letters = query.getQueryText().split("");
        String[] fieldContent = field.split("");

        for(int i = 0; i < letters.length; i ++){
            for(String l : fieldContent){
                if(l.equals(letters[i])) {
                    if (( countCns ++ ) == cns) {
                    return true;
                }
            }
            }
        }
    return false;
    }


    // rewrite this
    private boolean categorySearch(SearchQuery query){
        List<String> categoriesNames = categoryService.
                    allCategories().
                    stream().
                    map(Category::getCategory).collect(Collectors.toList());

        return categoriesNames.contains(query.getQueryText());
    }

}
