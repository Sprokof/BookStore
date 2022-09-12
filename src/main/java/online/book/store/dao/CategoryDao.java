package online.book.store.dao;

import online.book.store.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<String> allCategory();
    List<String> popularCategories();
    void saveCategoryIfAbsent(Category bookCategory);

}
