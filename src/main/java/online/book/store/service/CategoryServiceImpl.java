package online.book.store.service;

import online.book.store.dao.CategoryDao;
import online.book.store.entity.Category;
import online.book.store.entity.ExistCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Service
@Component
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryDao categoryDao;



    @Override
    public List<ExistCategory> getAllCategories() {
        return sort();
    }

    @Override
    public Category existCategory(String category) {
        return this.categoryDao.existCategory(new Category(category));
    }

    private List<ExistCategory> sort() {
    List<ExistCategory> categories = this.categoryDao.allCategories();
        categories.sort((c1, c2) -> c1.getCategory().length() - c2.getCategory().length());
        return categories;
    }
}
