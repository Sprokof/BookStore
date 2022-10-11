package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchQuery {
    private String queryText;

    public int length(){
        return this.queryText.length();
    }

    public SearchQuery(String queryText) {
        this.queryText = queryText;
    }
}
