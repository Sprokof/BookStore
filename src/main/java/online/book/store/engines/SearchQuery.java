package online.book.store.engines;


import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SearchQuery {
    private String queryText;
}
