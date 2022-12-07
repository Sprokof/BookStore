package online.book.store.dao;

import online.book.store.entity.UserSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

@Component
public class SessionDaoImpl implements SessionDao {

    private final SessionFactory sessionFactory =
            SessionFactoryInitialization.getInitializationFactory();

    @Override
    public void deleteSession(UserSession userSession) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.delete(session);
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
    public void deleteSessionById(String id) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM " +
                            "USERS_SESSIONS WHERE SESSION_ID=:id").
                    setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null)
                    session.getTransaction().commit();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public boolean sessionExist(String id) {
        Session session = null;
        UserSession userSession = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            userSession = (UserSession) session.createSQLQuery("SELECT * FROM USERS_SESSIONS " +
                            "WHERE SESSION_ID=:id").
                    setParameter("id", id).
                    addEntity(UserSession.class).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
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
        return userSession != null;
    }

    @Override
    public UserSession getSessionById(String sessionId) {
        Session session = null;
        UserSession userSession = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        userSession = (UserSession) session.createSQLQuery("SELECT * FROM " +
                "USERS_SESSIONS WHERE SESSION_ID=:id")
                .setParameter("id", sessionId)
                .addEntity(UserSession.class).getSingleResult();
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
    return userSession;
    }

    @Override
    public void updateSession(UserSession userSession) {
        Session session = null;
    try{
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.update(userSession);
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

