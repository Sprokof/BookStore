package online.book.store.service;

import online.book.store.dao.CategoryDao;
import online.book.store.dao.CategoryDaoImpl;
import online.book.store.dto.CategoryDto;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class CategoryServiceImpl implements CategoryService{

    private final CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<CategoryDto> getAllCategories() {
        return sort();
    }

    @Override
    public boolean existCategory(String category) {
        return this.categoryDao.existCategory(category) != null;
    }

    private List<CategoryDto> sort() {
    List<String> categories = this.categoryDao.allCategories();
        categories.sort((c1, c2) -> c1.length() - c2.length());
        return categories.stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        return this.categoryDao.getCategoryByName(categoryName);
    }

    @Override
    public List<Book> getBooksByCategories(String category) {
        return this.categoryDao.getCategoryByName(category).getBooks();
    }
}
