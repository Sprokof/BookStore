package online.book.store.dao;

import online.book.store.entity.Checkout;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckoutDaoImpl implements CheckoutDao{

    private final SessionFactory sessionFactory = SessionFactoryInitialization.getInitializationFactory();

    @Override
    public void updateCheckout(Checkout checkout) {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(checkout);
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
    }
}
