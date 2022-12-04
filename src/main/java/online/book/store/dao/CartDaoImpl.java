package online.book.store.dao;

import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.util.stream.Collectors;

@Component
public class CartDaoImpl implements CartDao{

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInitializationFactory();

    @Override
    public boolean contains(Cart cart, int bookId) {
        Session session = null;
        CartItem cartItem = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        cartItem = (CartItem) session.createSQLQuery("SELECT * FROM CARTS_ITEMS WHERE CART_ID=:c_id AND BOOK_ID=:b_id").
                setParameter("c_id", cart.getId()).
                setParameter("b_id", bookId).
                addEntity(CartItem.class).list().get(0);
        session.getTransaction().commit();
    }
    catch (Exception e) {
        if (session != null) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                if (e instanceof IndexOutOfBoundsException) return false;
            }
        }
    } finally {
        if (session != null) {
            session.close();
        }
    }
    return cartItem != null;
    }

    @Override
    public void updateCart(Cart cart) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.update(cart);
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
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.update(cartItem);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
    }


    @Override
    public CartItem getCartItemByBook(Cart cart, Book book) {
        Session session = null;
        CartItem cartItem = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        cartItem = (CartItem) session.createSQLQuery("SELECT * FROM " +
                "CARTS_ITEMS as item JOIN " +
                        "CARTS as cart on " +
                        "item.cart_id = cart.id WHERE item.book_id=:bookId AND cart.id=:cartId").
                addEntity(CartItem.class).
                setParameter("bookId", book.getId()).
                setParameter("cartId", cart.getId()).
                getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null){
            if(session.getTransaction() != null){
                session.getTransaction().rollback();
                if(e instanceof NoResultException){
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

    return cartItem;
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        Session session = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.delete(cartItem);
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null){
            if(session.getTransaction() != null){
                session.getTransaction().commit();
            }
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    }

    @Override
    public Integer getItemsQuantity(int cartId) {
        Session session = null;
        Integer quantity = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        quantity = (Integer) session.createSQLQuery("SELECT QUANTITY FROM CARTS " +
                "WHERE id=:cartId").
                setParameter("cartId", cartId).getSingleResult();
        session.getTransaction().commit();
    }
    catch(Exception e){
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
    return quantity;
    }

}
