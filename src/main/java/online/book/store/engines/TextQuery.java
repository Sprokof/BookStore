package online.book.store.engines;


import lombok.*;
import online.book.store.entity.Category;

@Getter
@Setter
@NoArgsConstructor
public class TextQuery implements SearchQuery{

    private String queryText;

    public TextQuery(String queryText){
        this.queryText = queryText;
    }


}
