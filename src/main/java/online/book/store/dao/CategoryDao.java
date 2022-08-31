package online.book.store.dao;

import online.book.store.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> allCategory();
    List<Category> popularCategories();
    void saveCategoryIfAbsent(Category bookCategory);

}
