package online.book.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import online.book.store.builder.AbstractCategoryBuilder;
import online.book.store.entity.Category;
import java.lang.reflect.Field;


@Getter
@Setter
public class CategoryDto extends AbstractCategoryBuilder {

    private String category;
    private boolean chosen;

    public CategoryDto(){
        this.chosen = false;
    }

    public CategoryDto(String category){
        this.category = category;
        this.chosen = false;
    }

    @Override
    public AbstractCategoryBuilder builder() {
        return this;
    }

    @Override
    public AbstractCategoryBuilder category(String category) {
        this.category = category;
        return this;
    }

    private boolean containsNull(){
        Field[] categoryFields =  this.getClass().getFields();
        for(Field field : categoryFields){
            if(field == null) return true;
        }
    return false;
    }

    @Override
    public Category build() {
        if(!containsNull()){
            return new Category(this.getCategory());
        }
    return null;
    }


    public Category doCategoryBuild(){
        return builder().
                    category(this.category).
                    build();
    }
}
