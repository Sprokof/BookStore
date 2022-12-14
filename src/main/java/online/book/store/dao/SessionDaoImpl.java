package online.book.store.dao;

import online.book.store.entity.UserSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.math.BigInteger;

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
        String creationTime = null;
        UserSession userSession = null;
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
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            userSession = (UserSession) session.createSQLQuery("SELECT * FROM " +
                            "USERS_SESSIONS WHERE SESSION_ID=:id")
                    .setParameter("id", sessionId)
                    .addEntity(UserSession.class).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) {
                        return null;
                    }
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return userSession;
    }

    @Override
    public boolean uniqueUserSession(String sessionid) {
        Session session = null;
        BigInteger countSessions = BigInteger.valueOf(0L);
        int userId = findUserId(sessionid);
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            countSessions = (BigInteger) session.createSQLQuery("SELECT COUNT(SESSION_ID) " +
                            "FROM USERS_SESSIONS WHERE USER_ID=:id").
                    setParameter("id", userId).getSingleResult();
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
        return countSessions.intValue() == 1;
    }


    private int findUserId(String sessionid) {
        Session session = null;
        int userId = 0;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            userId = (int) session.createSQLQuery("SELECT USER_ID " +
                            "FROM USERS_SESSIONS WHERE SESSION_ID=:id").
                    setParameter("id", sessionid).getSingleResult();
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
        return userId;
    }
}


