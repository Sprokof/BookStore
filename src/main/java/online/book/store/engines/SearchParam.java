package online.book.store.engines;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class SearchParam  {
    public static final String[] SEARCH_COLUMNS = {"isbn", "title", "authors", "description", "subject", "publisher"};

    private String query;
    private String sortType;

    public SearchParam(SearchQuery query, SortTypes sortType) {
        this.query = query.getQueryText();
        this.sortType = sortType.getType();
    }


    public String getQuery() {
        return query;
    }

    public void setQuery(SearchQuery query) {
        this.query = query.getQueryText();
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(SortTypes sortType) {
        this.sortType = sortType.getType();
    }


    public SortTypes currentType(){
        return SortTypes.getTypeByName(this.sortType);
    }

}
