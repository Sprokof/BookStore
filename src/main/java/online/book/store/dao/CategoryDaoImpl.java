package online.book.store.dao;

import online.book.store.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.List;

@Component
public class CategoryDaoImpl implements CategoryDao{

    private final SessionFactory sessionFactory = SessionFactorySingleton.
            getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<Category> allCategory() {
        Session session = null;
        List<Category> booksCategories = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            booksCategories = session.
                    createSQLQuery("SELECT * FROM BOOKS_CATEGORIES").
                    addEntity(Category.class).list();
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
    @SuppressWarnings("unchecked")
    public List<Category> popularCategories() {
        Session session = null;
        List<Category> result = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            result = session.createSQLQuery("SELECT * FROM " +
                            "BOOKS_CATEGORIES ORDER BY CATEGORY_RATING").
                    addEntity(Category.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if(session.getTransaction() != null){
                    session.getTransaction().rollback();
                }
            }
        }
        finally {
            if(session != null){
                session.close();
            }
        }
        if(result != null){
        result = getHighRatingCategory(result, 5);
        }

    return result;
    }

    @Override
    public void saveCategoryIfAbsent(Category bookCategory) {
        Session session = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        if(!exist(bookCategory)) {
            session.save(bookCategory);
        }
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    }

    private boolean exist(Category bookCategory){
        Session session = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("SELECT * FROM " +
                "BOOKS_CATEGORIES WHERE CATEGORY=:category").
                addEntity(Category.class).
                setParameter("category", bookCategory).
                getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
                if(e instanceof NoResultException) {
                    return false;
                }
            }
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    return true;
    }

    private List<Category> getHighRatingCategory(List<Category> categories, int count){
        Category[] temp = new Category[count];
        System.arraycopy(categories.toArray(Category[] :: new),
                0, temp, 0, temp.length);
        return Arrays.asList(temp);

    }
}
