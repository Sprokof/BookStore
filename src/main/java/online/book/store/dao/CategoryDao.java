package online.book.store.dao;

import online.book.store.entity.Category;
import online.book.store.entity.ExistCategory;

import java.util.List;

public interface CategoryDao {
    List<ExistCategory> allCategories();
    Category existCategory(Category category);


}
