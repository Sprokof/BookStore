package online.book.store.dao;

import online.book.store.entity.BookReview;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ReviewsDaoImpl implements ReviewsDao {

    private final SessionFactory sessionFactory =
            SessionFactoryInitialization.getInitializationFactory();

    @Override
    public int bookReviewCount(String isbn) {
        Session session = null;
        int count = 0;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        count = (int) session.createSQLQuery(sqlStatement(1)).
                setParameter("isbn", isbn).getSingleResult();
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
    return count;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BookReview> getBookReviews(String isbn) {
        Session session = null;
        List<BookReview> bookReviews = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            bookReviews = session.createSQLQuery(sqlStatement(0)).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return bookReviews;
    }

    private String sqlStatement(int index){
        String[] statements = {"SELECT * FROM " +
                "BOOK_REVIEWS as br JOIN BOOKS as b on" +
                " br.book_id=b.id WHERE ISBN=:isbn",
                "SELECT COUNT(ID) FROM BOOK_REVIEWS WHERE ISBN=:isbn"};
        return statements[index];
    }
}
