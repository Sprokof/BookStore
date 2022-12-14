package online.book.store.dao;

import online.book.store.entity.SessionStatistics;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Component;

@Component
public class SessionStatisticsDaoImpl implements SessionStatisticsDao {

    private final SessionFactory sessionFactory =
            SessionFactoryInitialization.getInitializationFactory();

    @Override
    public SessionStatistics getStatistics() {
        Session session  = null;
        SessionStatistics sessionStatistics = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        sessionStatistics = (SessionStatistics) session.get(SessionStatistics.class, 1);
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
    return sessionStatistics;
    }

    @Override
    public boolean updateStatistics(SessionStatistics statistics) {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(statistics);
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
            return false;
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
    return true;
    }
}
