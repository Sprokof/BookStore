package online.book.store.service;

import online.book.store.dao.CategoryDao;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Component
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> popularCategories() {
        return getPopularCategories();
    }

    @Override
    public List<Category> allCategories() {
        return this.categoryDao.allCategory();
    }

    private List<Category> getPopularCategories(){
    List<Category> categories;
      categories = this.categoryDao.allCategory();
      categories.sort(Comparator.comparingInt(Category::getRating));
    return getFewFirstCategories(categories);

    }

    private List<Category> getFewFirstCategories(List<Category> categories){
        Category[] sourceArray = categories.toArray(Category[]::new);
        Category[] targetArray = new Category[5];
        System.arraycopy(sourceArray, 0, targetArray, 0, 5);
    return Arrays.asList(targetArray);

    }
}
