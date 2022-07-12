package online.book.store.dao;

import online.book.store.entity.Category;
import online.book.store.singletons.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

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
                    createSQLQuery("SELECT * FROM CATEGORIES").
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
}
