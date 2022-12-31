package online.book.store.engines;

import lombok.Getter;
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
    private List<Page> pages;


    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;


    @Getter
    private List<SearchResult> searchResults;



    public SiteEngine executeSearchQuery(SearchQuery query) {
        if(!query.isStopWord()) {
            if (isCategory(query)) {
                String category = query.getQueryText();
                this.searchResults = this.categoryService.getBooksByCategories(category);
            } else {
                this.searchResults = findAndSort(query);
            }
        }
        if(hasResult()) initPages();
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
        List<Page.Row> rows = mapResultsToRow();
        int count = Page.MAX_COUNT_ROWS_IN_PAGE;
        this.pages = new LinkedList<>();
        for (int i = 0; i < rows.size(); i += count) {
            Page page = new Page(rows.subList(i, Math.min(i + count,
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

    private List<Page.Row> mapResultsToRow() {
        int count = Page.Row.MAX_COUNT_BOOKS_IN_ROWS;
        List<Page.Row> rows = new LinkedList<>();
        for (int i = 0; i < this.searchResults.size(); i += count) {
            rows.add(new Page.Row().setResultsInRow(this.searchResults.subList(i,
                    Math.min(i + count, this.searchResults.size()))));
        }
        return rows;
    }

    public List<Page.Row> mapBooksToRow(List<Book> booksToMap) {
        int count = Page.Row.MAX_COUNT_BOOKS_IN_ROWS;
        List<Page.Row> rows = new LinkedList<>();
        for (int i = 0; i < booksToMap.size(); i += count) {
            rows.add(new Page.Row().setBooksInRow(booksToMap.subList(i,
                    Math.min(i + count, booksToMap.size()))));
        }
        return rows;
    }


    public boolean hasResult() {
        return this.searchResults != null && !this.searchResults.isEmpty();
    }


    private List<SearchResult> findAndSort(SearchQuery query){
        List<SearchResult> results = this.bookService.findBooksBySearchQuery(query, SearchQuery.INDEXING_COLUMNS);
        if(!results.isEmpty()){
            results.sort(this::compareRotationValue);
        }
        return results;
    }

    private Page getSortedPage(Page page, SortTypes type) {
        switch (type) {
            case Relevance:
                for(Page.Row row : page.getRowsInPage()) {
                    row.getResultsInRow().sort(this::compareRotationValue);
                }
                break;
            case POPULARITY:
                for(Page.Row row : page.getRowsInPage()) {
                    row.getResultsInRow().sort(this::compareBookRating);
                }
                break;
            case LOWEST:
                for(Page.Row row : page.getRowsInPage()) {
                    row.getResultsInRow().sort((r1, r2) -> comparePrice(r1, r2, true));
                }
                break;
            case HIGHEST:
                for(Page.Row row : page.getRowsInPage()) {
                    row.getResultsInRow().sort((r1, r2) -> comparePrice(r1, r2, false));
                }
                break;
            case LATEST:
                for(Page.Row row : page.getRowsInPage()) {
                    row.getResultsInRow().sort(this::compareAddedDate);
                }
                break;
        }
        return page;

    }

}