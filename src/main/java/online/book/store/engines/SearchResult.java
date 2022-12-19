package online.book.store.engines;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.entity.Book;
import online.book.store.enums.RotationPriority;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private Book book;
    private RotationPriority priority;


    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof SearchResult)) return false;
        SearchResult result = (SearchResult) obj;
        return this.book.equals(result.book);

    }
}
