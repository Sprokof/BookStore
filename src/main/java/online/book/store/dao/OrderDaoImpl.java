package online.book.store.dao;

import online.book.store.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDao{

    private final SessionFactory sessionFactory = SessionFactorySingleton.getInitializationFactory();


    @Override
    public void deleteOrders(String statement) {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery(statement).executeUpdate();
        session.getTransaction().commit();
    }
    catch (Exception e){
        e.printStackTrace();
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    if(session != null){
        session.close();
    }
    }


    @Override
    public void updateOrder(Order order) {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(order);
        session.getTransaction().commit();
    }
    catch(Exception e){
        if(session != null && session.getTransaction() != null)
            session.getTransaction().rollback();
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    }
}
