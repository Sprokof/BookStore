package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.enums.RotationPriority;
import online.book.store.service.BookService;
import online.book.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


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


    @Getter
    private List<SearchResult> searchResults;

    @Getter
    private SearchParam searchParam;


    public SiteEngine executeSearchQuery(SearchQuery query, SortTypes type) {
        if (isCategory(query)) {
            String category = query.getQueryText();
            this.searchResults = this.categoryService.getBooksByCategories(category);
        } else {
            this.searchResults = this.bookService.findBooksByParam(query.getQueryText());
            //for (Book book : bookService.getAllBooks())
               // kmpMatcher(book, query);
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


    public void kmpMatcher(Book book, SearchQuery searchQuery) {
        int queryLength = searchQuery.length();
        if (queryLength == 0) return ;

        String queryText = searchQuery.getQueryText();
        Field[] fields = extractIndexingFields(book);
        for (Field field : fields) {
            String value = fieldValue(book, field);
            int fieldLength = value.length();
            int[] p = prefixFunction(searchQuery);
            for (int i = 0, k = 0; i < fieldLength; i++) {
                for (; ; k = p[k - 1]) {
                    if (String.valueOf(queryText.charAt(k)).
                            equalsIgnoreCase(String.valueOf(value.charAt(i)))) {
                        if (++k == queryLength) {
                            RotationPriority priority = RotationPriority.valueOfField(getFieldName(field));
                            SearchResult result = new SearchResult(book, priority);
                            this.searchResults.add(result);
                            return;
                        }
                        break;
                    }
                    if (k == 0) {
                        break;
                    }
                }
            }
        }
    }

    private boolean isCategory(SearchQuery query) {
        return this.categoryService.existCategory(query.getQueryText());
    }

    private void sortSearchResult() {
        if (this.searchResults.isEmpty()) return;
        switch (this.searchParam.currentType()) {
            case ROTATION:
                this.searchResults.sort(this::compareRotationValue);
                break;
            case POPULARITY:
                this.searchResults.sort(this::compareBookRating);
                break;
            case LOWEST:
                this.searchResults.sort((r1, r2) -> comparePrice(r1, r2, true));
                break;
            case HIGHEST:
                this.searchResults.sort((r1, r2) -> comparePrice(r1, r2, false));
                break;
            case LATEST:
                this.searchResults.sort(this::compareAddedDate);
                break;

        }
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


    public List<Row> mapResultToRow() {
        int rowSize = 4;
        this.rows = new LinkedList<>();
        for (int i = 0; i < this.searchResults.size(); i += rowSize) {
            Row row = new Row(this.searchResults.subList(i,
                    Math.min(i + rowSize, this.searchResults.size())).stream().
                    map(SearchResult::getBook).collect(Collectors.toList()));
            rows.add(row);
        }
        return this.rows;
    }

    public List<Row> mapBooksToRow(List<Book> booksToMap) {
        int rowSize = 4;
        this.rows = new LinkedList<>();
        for (int i = 0; i < booksToMap.size(); i += rowSize) {
            Row row = new Row(booksToMap.subList(i,
                            Math.min(i + rowSize, this.searchResults.size())));
            rows.add(row);
        }
        return this.rows;
    }


    public boolean hasResult() {
        return !this.searchResults.isEmpty();
    }

    private SiteEngine saveParams(SearchQuery searchQuery, SortTypes sortType) {
        this.searchParam = new SearchParam(searchQuery, sortType);
        return this;
    }


    private Field[] extractIndexingFields(Book book){
        Field[] booksFields = book.getClass().getDeclaredFields();
        return Arrays.stream((booksFields)).
                filter((this::fieldIndexing)).
                collect(Collectors.toList()).toArray(Field[]::new);
    }


    private boolean fieldIndexing(Field field){
        String fieldName = getFieldName(field);
        return fieldName.equals("isbn") || fieldName.equals("title") ||
                fieldName.equals("description") || fieldName.equals("authors") ||
        fieldName.equals("subject") || fieldName.equals("publisher");
    }

    private String getFieldName(Field field){
        field.setAccessible(true);
        return field.getName();
    }

    private String fieldValue(Book book, Field field) {
        String result = "";
        field.setAccessible(true);
        try {
            String value = field.get(book).toString();
            if (value != null) {
                result = value;
            }
        } catch (Exception e) {
            return result;
        }
        return result.replaceAll("\\s", "").toLowerCase(Locale.ROOT);
    }

}