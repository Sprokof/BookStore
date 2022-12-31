package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Locale;

@Getter
@Setter
public class SearchQuery {
    public static final String[] INDEXING_COLUMNS = {"isbn", "title", "authors", "description", "subject", "publisher"};
    private static final List<String> STOP_WORDS = List.of("the", "be", "to",
            "of", "and", "a", "in", "that", "have", "i", "it", "for",
            "not", "on", "with", "he", "as", "you", "do", "at", "this", "but", "his", "by", "from");
    private String queryText;

    public int length(){
        return this.queryText.length();
    }

    public SearchQuery(String queryText) {
        this.queryText = queryText;
    }

    public boolean isStopWord(){
        return STOP_WORDS.contains(this.queryText.toLowerCase(Locale.ROOT));
    }
}
