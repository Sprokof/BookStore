package online.book.store.service;


import online.book.store.dto.CategoryDto;
import online.book.store.entity.Category;
import online.book.store.entity.ExistCategory;

import java.util.List;

public interface CategoryService {
    boolean existCategory(String category);
    List<ExistCategory> getAllCategories();
}
