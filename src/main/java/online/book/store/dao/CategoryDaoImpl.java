package online.book.store.dao;

import online.book.store.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import static online.book.store.entity.BookReview.*;

import java.util.List;

@Component
public class CategoryDaoImpl implements CategoryDao{

    private final SessionFactory sessionFactory = SessionFactoryInitialization.
            getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<String> allCategories() {
        Session session = null;
        List<String> booksCategories = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            booksCategories = session.
                    createSQLQuery("SELECT CATEGORY FROM CATEGORIES LIMIT(10)").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }

        }
        return booksCategories;
    }



    @Override
    public Category existCategory(String category) {
        Session session = null;
        Category findCategory = null;
    try {
        session = this.sessionFactory.openSession();;
        session.beginTransaction();
        findCategory = (Category) session.
                createSQLQuery("SELECT * FROM CATEGORIES WHERE CATEGORY=:category")
                .addEntity(Category.class).
                setParameter("category", category).list().get(0);
        session.getTransaction().commit();
    }
    catch (Exception e){
            if(session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if(e instanceof IndexOutOfBoundsException){
                        return null;
                    }
                }
            }
    }

    finally {
        if(session != null){
            session.close();
        }
    }
    return findCategory;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        Session session = null;
        Category category = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        category =  (Category) session.createSQLQuery("SELECT * FROM " +
                "CATEGORIES WHERE CATEGORY=:category").
                setParameter("category", categoryName).
                addEntity(Category.class).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    return category;
    }

    @Override
    public void updateCategory(Category category) {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(category);
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    }

    @Override
    @SuppressWarnings ("unchecked")
    public List<String> getPopularCategories() {
        Session session = null;
        List<String> categories = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        categories = (List<String>) session.createSQLQuery("SELECT CATEGORY FROM CATEGORIES WHERE " +
                "CATEGORY_RATING BETWEEN :min AND :max ORDER BY CATEGORY_RATING limit (10)").setParameter("min",
                MIN_POPULAR_RATING).
                setParameter("max", MAX_POPULAR_RATING).list();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    return categories;
    }
}
