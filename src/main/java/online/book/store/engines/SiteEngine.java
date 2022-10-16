package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


@Component
public class SiteEngine {

    @Getter
    @Setter
    private List<Row> rows = new LinkedList<>();

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Row {
        List<Book> bookInRow;
    }


    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    private List<Book> bookList;

    @Getter
    private SearchParam searchParam;


    public SiteEngine executeSearchQuery(SearchQuery query, SortTypes type) {
        if (isCategory(query)) {
            String category = query.getQueryText();
            this.bookList = this.categoryService.getBooksByCategories(category);
        } else {
            this.bookList = new LinkedList<>();
            for (Book book : bookService.getAllBooks()) {
                if (kmpMatcher(book, query)) {
                    this.bookList.add(book);
                }
            }
        }

        saveParams(query, type).sortSearchResult();
        return this;
    }

    private int[] prefixFunction(SearchQuery query) {
        String queryText = query.getQueryText();
        int[] p = new int[query.length()];
        int k = 0;
        for (int i = 1; i < query.length(); i++) {
            while (k > 0 && queryText.charAt(k) != queryText.charAt(i)) {
                k = p[k - 1];
            }
            if (queryText.charAt(k) == queryText.charAt(i)) {
                ++k;
            }
            p[i] = k;
        }
        return p;
    }


    public boolean kmpMatcher(Book book, SearchQuery searchQuery) {
        String booksContent = book.toString();
        int queryLength = searchQuery.length();
        if (queryLength == 0) {
            return false;
        }
        String text = searchQuery.getQueryText();
        int[] p = prefixFunction(searchQuery);
        for (int i = 0, k = 0; i < booksContent.length(); i++) {
            for (; ; k = p[k - 1]) {
                if (String.valueOf(text.charAt(k)).equalsIgnoreCase(String.valueOf(booksContent.charAt(i)))) {
                    if (++k == queryLength) {
                        return true;
                    }
                    break;
                }
                if (k == 0) {
                    break;
                }
            }
        }
        return false;
    }

    private boolean isCategory(SearchQuery query) {
        return this.categoryService.existCategory(query.getQueryText());
    }

    private void sortSearchResult() {
        if (this.bookList.isEmpty()) return;
        switch (this.searchParam.currentType()) {
            case POPULARITY:
                this.bookList.sort(Comparator.comparingDouble(Book::getBookRating));
                break;
            case LOWEST:
                this.bookList.sort(Comparator.comparingDouble(Book::getPrice));
                break;
            case HIGHEST:
                this.bookList.sort((book, bookNext) -> {
                    return ((int) bookNext.getPrice() - (int) book.getPrice());
                });
                break;
            case LATEST:
                this.bookList.sort((book, bookNext) ->
                        compareAddedDate(book.getAddedDate(), bookNext.getAddedDate()));
                break;

        }
    }

    private int compareAddedDate(LocalDate firstDate, LocalDate secondDate) {
        int result = 0;
        if (firstDate.isAfter(secondDate)) {
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }


    public List<Row> mapResultToRow() {
        int rowSize = 4;
        this.rows = new LinkedList<>();
        for (int i = 0; i < this.bookList.size(); i += rowSize) {
            Row row = new Row(this.bookList.subList(i,
                    Math.min(i + rowSize, this.bookList.size())));
            rows.add(row);
        }
        return this.rows;
    }

    public List<Row> mapBooksToRow(List<Book> booksToMap) {
        int rowSize = 4;
        this.rows = new LinkedList<>();
        for (int i = 0; i < booksToMap.size(); i += rowSize) {
            Row row = new Row(booksToMap.subList(i,
                    Math.min(i + rowSize, booksToMap.size())));
            rows.add(row);
        }
        return this.rows;
    }

    public boolean hasResult() {
        return !this.bookList.isEmpty();
    }

    private SiteEngine saveParams(SearchQuery searchQuery, SortTypes sortType) {
        this.searchParam = new SearchParam(searchQuery, sortType);
        return this;
    }

}