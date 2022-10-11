package online.book.store.dao;

import online.book.store.engines.SiteEngine;
import online.book.store.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory =
            SessionFactorySingleton.getInitializationFactory();

    @Override
    public void saveUser(User user) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
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
    @SuppressWarnings("unchecked")
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
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        String column = "USERNAME";

        if (email.matcher(login).find()) {
            column = "EMAIL";
        }
        else {
            login = SiteEngine.firstLetterToUpperCase(login);
        }

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

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getUsersInSession() {
        Session session = null;
        List<User> users = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            users = session.createSQLQuery("SELECT * FROM " +
                    "USERS WHERE IN_SESSION is true").addEntity(User.class).list();
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
        return users;

    }

    @Override
    public boolean existUUID(UUID uuid) {
        String stringUUID = uuid.toString();
        Session session = null;
        String value = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            value = (String) session.createSQLQuery("SELECT USER_ID FROM " +
                            "USERS WHERE USER_ID=:uuid").
                    setParameter("uuid", stringUUID).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                if (session.getTransaction() != null) {
                    session.getTransaction().rollback();
                    if (e instanceof NoResultException) {
                        return false;
                    }
                }
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return value != null;
    }

    @Override
    public User getUserByUUID(UUID uuid) {
        String stringUUID = uuid.toString();
        Session session = null;
        User user = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            user = (User) session.createSQLQuery("SELECT * FROM " +
                            "USERS WHERE USER_ID=:uuid").
                    setParameter("uuid", stringUUID).addEntity(User.class).
                    getSingleResult();
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
        return user;
    }

}


