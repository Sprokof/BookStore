package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQuery {
    public static final String[] INDEXING_COLUMNS = {"isbn", "title", "authors", "description", "subject", "publisher"};
    private String queryText;

    public int length(){
        return this.queryText.length();
    }

    public SearchQuery(String queryText) {
        this.queryText = queryText;
    }
}
