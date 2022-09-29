package online.book.store.dao;


import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;

@Component
public class WishlistDaoImpl implements WishlistDao{

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInitializationFactory();

    @Override
    public boolean contains(Book book, Wishlist wishlist) {
        Session session = null;
        Integer id = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            id = (Integer) session.createSQLQuery("SELECT book_id " +
                            "FROM BOOKS_WISHLISTS WHERE book_id=:b_id AND wishlist_id=:w_id").
                    setParameter("b_id", book.getId()).
                    setParameter("w_id", wishlist.getId()).list().get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof ArrayIndexOutOfBoundsException) {
                        return false;
                    }
                }
            }
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
       return id != null;
    }

    @Override
    public void updateWishlist(Wishlist wishlist) {
        Session session = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(wishlist);
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
}

