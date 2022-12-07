package online.book.store.dao;


import online.book.store.config.MailConfig;
import online.book.store.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.regex.Pattern;

@Component
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory =
            SessionFactoryInitialization.getInitializationFactory();

    @Override
    public void saveUser(User user) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
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
    public void saveOrUpdate(User user) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.saveOrUpdate(user);
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
    public User getUserByLogin(String login) {
        String column = defineColumn(login);
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM USERS " +
                            "WHERE " + column + "=:login").addEntity(User.class).
                    setParameter("login", login).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.update(user);
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
    public User getUserByToken(String token) {
        Session session = null;
        User user = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM USERS " +
                            "WHERE TOKEN=:token").setParameter("token", token).
                    addEntity(User.class).list().get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof IndexOutOfBoundsException)
                        return null;
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return user;
    }

    @Override
    public boolean loginExist(String login) {
        Session session = null;
        String column = defineColumn(login);
        Integer id = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        id = (Integer) session.createSQLQuery("SELECT id FROM USERS " +
                "WHERE "+ column + "=:login").
                setParameter("login", login).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
            if(e instanceof NoResultException) return false;
        }
    }
    finally {
        if(session != null){
            session.close();
        }
    }
        return (id != null);
    }

    private String defineColumn(String login){
        Pattern email = Pattern.compile(MailConfig.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        if (email.matcher(login).find())  return "EMAIL";
        return "USERNAME";
    }


    @Override
    public boolean userAccept(String login) {
        Session session = null;
        String column = defineColumn(login);
        boolean accepted = false;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        accepted = (boolean) session.createSQLQuery("SELECT ACCEPTED FROM USERS WHERE " +
            column + "=:" + login).setParameter("login", login).getSingleResult();
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
    return accepted;
    }
}

