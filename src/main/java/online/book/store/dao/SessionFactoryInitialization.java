package online.book.store.dao;

import online.book.store.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryInitialization {
    private static SessionFactory instance;

    private SessionFactoryInitialization(){}

    public static SessionFactory getInitializationFactory() {
        if (instance == null) {
            instance = getSessionFactory(new Class[]{Book.class,
                                        Category.class, User.class, CartItem.class, Cart.class,
                                        BookReview.class,
                    Wishlist.class, SessionStatistics.class, Checkout.class,
                    Order.class, UserSession.class, OrderDetails.class, WaitList.class});
        }
    return instance;
    }

    private static SessionFactory getSessionFactory(Class<?>[] annotatedClass) {
        Configuration configuration = new Configuration();
        for (Class<?> c : annotatedClass) {
            configuration.addAnnotatedClass(c);
        }
        return configuration.configure("hibernate.cfg.xml").buildSessionFactory();

    }
}
