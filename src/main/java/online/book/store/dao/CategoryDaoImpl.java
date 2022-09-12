package online.book.store.dao;

import online.book.store.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class CategoryDaoImpl implements CategoryDao{

    private final SessionFactory sessionFactory = SessionFactorySingleton.
            getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<String> allCategory() {
        Session session = null;
        List<String> booksCategories = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            booksCategories = session.
                    createSQLQuery("SELECT CATEGORY FROM CATEGORIES").list();
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
    public List<String> popularCategories() {
        Session session = null;
        List<String> result = new LinkedList<>();
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            result = session.createSQLQuery("SELECT CATEGORY FROM CATEGORIES").list();
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
        if(!result.isEmpty()) {
            return extractSubList(result);
        }

        System.out.println(allCategory());
        return extractSubList(allCategory());
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

    private List<String> extractSubList(List<String> categories){
        return categories.subList(0, 5);

    }
}
