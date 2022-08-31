package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SearchQuery {
    private String queryText;

    public int length(){
        return this.queryText.length();
    }
}
