package online.book.store.engines;

import lombok.Getter;
import lombok.Setter;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
public class SearchQuery {

    public static final String[] INDEXING_COLUMNS = {"isbn", "title", "authors", "description", "subject", "publisher"};
    private static final List<String> STOP_WORDS = List.of("the", "be", "to",
            "of", "and", "a", "in", "that", "have", "i", "it", "for",
            "not", "on", "with", "he", "as", "you", "do", "at", "this", "but", "his", "by", "from");

    private String queryText;
    private SortType type;
    private String pageNumber;

    private List<SearchResult> results;
    private List<Page> pages;

    private final BookService bookService;
    private final CategoryService categoryService;


    @Autowired
    private SearchQuery(String queryText, SortType type, String pageNumber,
                       BookService bookService, CategoryService categoryService) {
        this.queryText = queryText;
        this.type = type;
        this.pageNumber = pageNumber;
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    private boolean isStopWord(){
        return STOP_WORDS.contains(this.queryText.toLowerCase(Locale.ROOT));
    }

    public static SearchQuery init(Map<String, String> requestParams,
                                   BookService bookService, CategoryService categoryService){
        String queryText = requestParams.get("query");
        SortType type = SortType.getTypeByName(requestParams.get("type"));
        String pageNumber = requestParams.get("page");
        return new SearchQuery(queryText, type, pageNumber, bookService, categoryService);
    }

    public SearchQuery execute(){
        if(!wrongQuery()){
            if(isCategory(this)) {
                this.results = this.categoryService.getBooksByCategories(queryText);
            }
            else this.results = findAndSort(this);
        }
        return this;
    }

    private boolean isCategory(SearchQuery query) {
        return this.categoryService.existCategory(query.getQueryText());
    }

    private List<SearchResult> findAndSort(SearchQuery query) {
        List<SearchResult> results = this.bookService.findBooksBySearchQuery(query, SearchQuery.INDEXING_COLUMNS);
        if (!results.isEmpty()) {
            results.sort(SearchResult::compareResults);
        }
        return results;
    }

    public boolean hasResults() {
        boolean hasResults = this.results != null && !this.results.isEmpty();
        if(hasResults) initPages();
        return hasResults;
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

    public Page getPage(){
        int index = (Integer.parseInt(this.pageNumber) - 1);
        Page page = this.pages.get(index);
        page.setCurrentPage(Integer.parseInt(pageNumber));
        page.setPagesCount(this.pages.size());
        return getSortedPage(page, type);
    }


    private List<Page.Row> mapResultsToRow() {
        int count = Page.Row.MAX_COUNT_BOOKS_IN_ROWS;
        List<Page.Row> rows = new LinkedList<>();
        for (int i = 0; i < this.results.size(); i += count) {
            rows.add(new Page.Row().setResultsInRow(this.results.subList(i,
                    Math.min(i + count, this.results.size()))));
        }
        return rows;
    }

    private int compareAddedDate(SearchResult resultFirst, SearchResult resultSecond) {
        int result = 0;
        LocalDate firstDate = resultFirst.getBook().getAddedDate();
        LocalDate secondDate = resultSecond.getBook().getAddedDate();
        if (firstDate.isAfter(secondDate)) {
            result = - 1;

        } else {
            result = 1;
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

    private int comparePrice(SearchResult resultFirst, SearchResult resultSecond, boolean flag){
        double priceOne = resultFirst.getBook().getPrice();
        double priceTwo = resultSecond.getBook().getPrice();
        int result = 0;
        if(priceOne < priceTwo){
            if(flag) result = 1;
            else result = - 1;
        }
        else {
            if(flag) result = - 1;
            else result =  1;
        }
        return result;
    }

    private Page getSortedPage(Page page, SortType type) {
        switch (type) {
            case RELEVANCE:
                page.getAllResults().sort(SearchResult::compareResults);
                break;

            case POPULARITY:
                page.getAllResults().sort(this::compareBookRating);
                break;

            case LOWEST:
                page.getAllResults().sort((r1, r2) -> comparePrice(r1, r2, false));
                break;

            case HIGHEST:
                page.getAllResults().sort((r1, r2) -> comparePrice(r1, r2, true));
                break;

            case LATEST:
                page.getAllResults().sort(this::compareAddedDate);
                break;
        }
        return page.createRows();

    }

    private boolean wrongQuery(){
        return this.isStopWord() || this.queryText.length() <= 2;
    }
}
