package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
public class SortConfig {
    private SortType sortType;

    public SortType currentType(){
        return this.sortType;
    }
}
