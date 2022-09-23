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
        return extractSubList(booksCategories);
    }


    private List<String> extractSubList(List<String> categories){
        if(categories.size() >= 10) {
            return categories.subList(0, 10);
        }
        return categories;

    }

    @Override
    public boolean existCategory(Category category) {
        Session session = null;
        Category findCategory = null;
    try {
        session = this.sessionFactory.openSession();;
        session.beginTransaction();
        findCategory = (Category) session.
                createSQLQuery("SELECT * FROM CATEGORIES WHERE CATEGORY=:category")
                .addEntity(Category.class).
                setParameter("category", category.getCategory()).
                getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
            if(session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                }
            }
    }

    finally {
        if(session != null){
            session.close();
        }
    }
    return findCategory != null;
    }
}
