package online.book.store.engines;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SortConfig {

   private SortTypes selectedType = SortTypes.DEFAULT;

   public static SortTypes[] sortTypes(){
        return new SortTypes[]{
                SortTypes.POPULARITY,
                SortTypes.HIGHEST,
                SortTypes.LOWEST,
                SortTypes.LATEST,
        };
    }
}
