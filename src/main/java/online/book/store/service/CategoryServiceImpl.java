package online.book.store.service;

import online.book.store.dao.CategoryDao;
import online.book.store.dao.CategoryDaoImpl;
import online.book.store.dto.CategoryDto;
import online.book.store.engines.SearchResult;
import online.book.store.entity.Book;
import online.book.store.entity.Category;
import online.book.store.enums.RelevancePriority;
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
    public List<SearchResult> getBooksByCategories(String category) {
        List<Book> books = this.categoryDao.getCategoryByName(category).getBooks();
        return books.stream().map((book) ->
                new SearchResult(book, RelevancePriority.A)).collect(Collectors.toList());
    }

    @Override
    public void updateCategory(Category category) {
        this.categoryDao.updateCategory(category);
    }

    @Override
    public List<CategoryDto> getPopularCategories() {
        return this.categoryDao.getPopularCategories().
                stream().map(CategoryDto :: new).collect(Collectors.toList());
    }
}
