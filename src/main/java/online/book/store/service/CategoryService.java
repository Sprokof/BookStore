package online.book.store.service;


import online.book.store.dto.CategoryDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;

import java.util.List;

public interface CategoryService {
    boolean existCategory(String category);
    List<CategoryDto> getAllCategories();
    Category getCategoryByName(String categoryName);
    List<Book> getBooksByCategories (String category);
    void updateCategory(Category category);
    List<CategoryDto> getPopularCategories();
}
