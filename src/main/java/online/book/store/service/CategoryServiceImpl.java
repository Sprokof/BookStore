package online.book.store.service;

import online.book.store.dao.CategoryDao;
import online.book.store.dto.CategoryDto;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<CategoryDto> popularCategories() {
        return this.categoryDao.popularCategories().
                stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> allCategories() {
        return this.categoryDao.allCategory().
                stream().map(CategoryDto::new).collect(Collectors.toList());
    }

}
