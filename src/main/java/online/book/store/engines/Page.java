package online.book.store.engines;

import lombok.Getter;
import lombok.Setter;
import online.book.store.entity.Book;


import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Page {
    public static final int MAX_COUNT_ROWS_IN_PAGE = 2;

    private List<Row> rowsInPage;
    private List<SearchResult> allResults;

    int pagesCount;
    int currentPage;

    public Page(List<Row> rowsInPage) {
        this.rowsInPage = rowsInPage;
        this.allResults = new LinkedList<>();
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

    public List<SearchResult> getAllResults(){
        this.rowsInPage.forEach((row) -> this.allResults.addAll(row.resultsInRow));
        return this.allResults;
    }

    public Page rebuildRows(){
        this.rowsInPage = new LinkedList<>();
        for (int i = 0; i < this.allResults.size(); i += Row.MAX_COUNT_BOOKS_IN_ROWS) {
            rowsInPage.add(new Row().setResultsInRow(this.allResults.subList(i,
                    Math.min(i + Row.MAX_COUNT_BOOKS_IN_ROWS, this.allResults.size()))));
        }
    return this;
    }
}
