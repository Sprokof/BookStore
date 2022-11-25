package online.book.store.dao;

import online.book.store.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<String> allCategories();
    List<String> getPopularCategories();
    Category existCategory(String category);
    Category getCategoryByName(String categoryName);
    void updateCategory(Category category);


}
