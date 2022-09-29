package online.book.store.dao;

import online.book.store.entity.Category;
import online.book.store.entity.ExistCategory;
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
    public List<ExistCategory> allCategories() {
        Session session = null;
        List<ExistCategory> booksCategories = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            booksCategories = session.
                    createSQLQuery("SELECT * FROM EXIST_CATEGORIES").
                    addEntity(ExistCategory.class).list();
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


    private List<ExistCategory> extractSubList(List<ExistCategory> categories){
        if(categories.size() >= 10) {
            return categories.subList(0, 10);
        }
        return categories;

    }

    @Override
    public Category existCategory(Category category) {
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
    return findCategory;
    }
}
