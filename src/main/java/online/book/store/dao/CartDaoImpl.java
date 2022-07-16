package online.book.store.dao;

import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.stream.Collectors;

@Component
public class CartDaoImpl implements CartDao{

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInitializationFactory();

    @Override
    public boolean bookAdded(Integer userId, Book book) {
        Session session = null;
        Cart userCart = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        userCart = (Cart) session.createSQLQuery("SELECT * FROM CARTS as cart JOIN TABLE " +
                        "USERS as u on u.id = cart.user_id WHERE u.id=:userId").
                setParameter("userId", userId).addEntity(Cart.class).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e) {
        if (session != null) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                if (e instanceof NoResultException) return false;
            }
        }
    } finally {
        if (session != null) {
            session.close();
        }
    }
    if((userCart != null)){
    String isbn = book.getIsbn();
    return (userCart.getCartItems().stream().
                map(CartItem::getIsbn).
                collect(Collectors.toList()).contains(isbn));
    }
    return false;
    }

    @Override
    public void updateCart(Cart cart) {
        Session session = null;
        Cart userCart = null;
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
    public CartItem getCartItemById(int id) {
        Session session = null;
        CartItem cartItem = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        cartItem = (CartItem) session.createSQLQuery("SELECT * FROM CART_ITEMS WHERE id=:id")
                .setParameter("id", id).addEntity(CartItem.class).getSingleResult();
        session.getTransaction().commit();
    } catch (Exception e) {
        if (session != null) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
                if(e instanceof NoResultException) return null;
            }
        }
    } finally {
        if (session != null) {
            session.close();
        }
    }
    return cartItem;
    }
}
