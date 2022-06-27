package online.book.store.dao;

import online.book.store.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
    private static SessionFactory instance;

    private SessionFactorySingleton (){}

    public static SessionFactory getInitializationFactory() {
        if (instance == null) {
            instance = getSessionFactory(new Class[]{Book.class,
                                        Category.class, User.class, CartItem.class, Cart.class,
                                        BookReview.class, BookRating.class});
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
