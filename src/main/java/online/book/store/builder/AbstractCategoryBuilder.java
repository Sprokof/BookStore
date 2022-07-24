package online.book.store.builder;

import online.book.store.entity.Category;

public abstract class AbstractCategoryBuilder {


    public AbstractCategoryBuilder builder(){
        return this;
    }

    public AbstractCategoryBuilder category(String category){
        return this;
    }

    public Category build(){
        return null;
    }
}
