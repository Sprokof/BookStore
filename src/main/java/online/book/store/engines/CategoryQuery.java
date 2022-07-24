package online.book.store.engines;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.book.store.entity.Category;

@NoArgsConstructor
@Setter
public class CategoryQuery implements SearchQuery{

    private String category;

    public CategoryQuery(String category){
        this.category = category;

    }

    public Category getCategory(){
        return new Category(this.category);
    }
}
