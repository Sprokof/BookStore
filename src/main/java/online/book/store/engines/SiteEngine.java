package online.book.store.engines;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.entity.Book;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class SiteEngine {

    @Getter
    @Setter
    private List<Row> rows = new LinkedList<>();

    @Getter
    @Setter
    private List<Page> pages;

    @Setter
    @Getter
    public static class Page {
        List<Row> rowsInPage;
        int pagesCount;
        int currentPage;

        public Page(List<Row> rowsInPage) {
            this.rowsInPage = rowsInPage;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Row {
        List<SearchResult> resultsInRows;
        List<Book> booksInRows;

        public Row setResultsInRows(List<SearchResult> resultsInRows) {
            this.resultsInRows = resultsInRows;
            return this;
        }

        public Row setBooksInRows(List<Book> booksInRows) {
            this.booksInRows = booksInRows;
            return this;
        }
    }

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;


    @Getter
    private List<SearchResult> searchResults;



    public SiteEngine executeSearchQuery(SearchQuery query, SortTypes type) {
        if (isCategory(query)) {
            String category = query.getQueryText();
            this.searchResults = this.categoryService.getBooksByCategories(category);
        } else {
            this.searchResults = findAndSort(query);
        }
        if(!searchResults.isEmpty()) initPages();
        return this;
    }


    private boolean isCategory(SearchQuery query) {
        return this.categoryService.existCategory(query.getQueryText());
    }


    private int compareAddedDate(SearchResult resultFirst, SearchResult resultSecond) {
        int result = 0;
        LocalDate firstDate = resultFirst.getBook().getAddedDate();
        LocalDate secondDate = resultSecond.getBook().getAddedDate();
        if (firstDate.isAfter(secondDate)) {
            result = 1;

        } else {
            result = - 1;
        }
        return result;
    }

    private int compareBookRating(SearchResult resultFirst, SearchResult resultSecond){
        double ratingOne = resultFirst.getBook().getBookRating();
        double ratingTwo = resultSecond.getBook().getBookRating();
        int result = 0;
        if(ratingOne < ratingTwo){
            result = 1;
        }
        else result = - 1;
        return result;
    }

    private int comparePrice(SearchResult resultFirst, SearchResult resultSecond, boolean desc){
        double priceOne = resultFirst.getBook().getPrice();
        double priceTwo = resultSecond.getBook().getPrice();
        int result = 0;
        if(priceOne < priceTwo){
            if(desc) result = - 1;
            else result = 1;
        }
        else
            if(desc) result = 1;
            else result = - 1;
        return result;
    }

    private int compareRotationValue(SearchResult resultFirst, SearchResult resultSecond){
        int valueFirst = resultFirst.getPriority().getValue();
        int valueSecond = resultSecond.getPriority().getValue();
        return valueSecond - valueFirst;
    }


    private void initPages() {
        List<Row> rows = mapResultToRow();
        int pageSize = 1;
        this.pages = new LinkedList<>();
        for (int i = 0; i < rows.size(); i += pageSize) {
            Page page = new Page(rows.subList(i, Math.min(i + pageSize,
                    rows.size())));
            this.pages.add(page);
          }

    }

    public Page getPage(String pageNumber, SortTypes type){
        int index = (Integer.parseInt(pageNumber) - 1);
        Page page = this.pages.get(index);
        page.setCurrentPage(Integer.parseInt(pageNumber));
        page.setPagesCount(this.pages.size());
        return getSortedPage(page, type);
    }

    private List<Row> mapResultToRow() {
        int rowSize = 2;
        List<Row> rows = new LinkedList<>();
        for (int i = 0; i < this.searchResults.size(); i += rowSize) {
            rows.add(new Row().setResultsInRows(this.searchResults.subList(i,
                    Math.min(i + rowSize, this.searchResults.size()))));
        }
        return rows;
    }

    public List<Row> mapBooksToRow(List<Book> booksToMap) {
        int rowSize = 4;
        this.rows = new LinkedList<>();
        for (int i = 0; i < booksToMap.size(); i += rowSize) {
            rows.add(new Row().setBooksInRows(booksToMap.subList(i,
                    Math.min(i + rowSize, booksToMap.size()))));
        }
        return this.rows;
    }


    public boolean hasResult() {
        return !this.searchResults.isEmpty();
    }


    private List<SearchResult> findAndSort(SearchQuery query){
        List<SearchResult> results = this.bookService.findBooksBySearchQuery(query, SearchQuery.INDEXING_COLUMNS);
        if(!results.isEmpty()){
            results.sort(this::compareRotationValue);
        }
        return results;
    }

    private SiteEngine.Page getSortedPage(Page page, SortTypes type) {
        switch (type) {
            case Relevance:
                for(Row row : page.getRowsInPage()) {
                    row.getResultsInRows().sort(this::compareRotationValue);
                }
                break;
            case POPULARITY:
                for(Row row : page.getRowsInPage()) {
                    row.getResultsInRows().sort(this::compareBookRating);
                }
                break;
            case LOWEST:
                for(Row row : page.getRowsInPage()) {
                    row.getResultsInRows().sort((r1, r2) -> comparePrice(r1, r2, true));
                }
                break;
            case HIGHEST:
                for(Row row : page.getRowsInPage()) {
                    row.getResultsInRows().sort((r1, r2) -> comparePrice(r1, r2, false));
                }
                break;
            case LATEST:
                for(Row row : page.getRowsInPage()) {
                    row.getResultsInRows().sort(this::compareAddedDate);
                }
                break;
        }
        return page;

    }

}