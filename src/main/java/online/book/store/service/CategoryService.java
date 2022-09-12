package online.book.store.service;


import online.book.store.dto.CategoryDto;
import online.book.store.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> allCategories();
    List<CategoryDto> popularCategories();
}
