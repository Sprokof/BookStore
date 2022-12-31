package online.book.store.engines;

import lombok.Getter;
import lombok.Setter;
import online.book.store.entity.Book;

import java.util.List;

@Getter
@Setter
public class Page {
    public static final int MAX_COUNT_ROWS_IN_PAGE = 2;

    private List<Row> rowsInPage;
    int pagesCount;
    int currentPage;

    public Page(List<Row> rowsInPage) {
        this.rowsInPage = rowsInPage;
    }

    @Getter
    public static class Row {
        public static final int MAX_COUNT_BOOKS_IN_ROWS = 4;
        List<Book> booksInRow;
        List<SearchResult> resultsInRow;

        public Row setBooksInRow(List<Book> books) {
            this.booksInRow = books;
            return this;
        }

        public Row setResultsInRow(List<SearchResult> resultsInRow) {
            this.resultsInRow = resultsInRow;
            return this;
        }
    }
}
