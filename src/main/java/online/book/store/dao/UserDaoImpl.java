package online.book.store.dao;

import online.book.store.entity.User;
import online.book.store.singletons.SessionFactorySingleton;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.util.regex.Pattern;

@Component
public class UserDaoImpl implements UserDao{

    private final SessionFactory sessionFactory =
            SessionFactorySingleton.getInitializationFactory();

    @Override
    public User saveOrGetUser(User user) {
        User temp = null;
        Session session = null;
    try{
        session = sessionFactory.openSession();
        session.beginTransaction();
    try {
        temp = (User) session.createSQLQuery("SELECT * FROM USERS WHERE EMAIL=:email").
                setParameter("email", user.getEmail()).
                addEntity(User.class).getSingleResult();
    }
    catch (NoResultException e){ temp = null; }

        if (temp == null) {
            session.save(user);
        }
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
    if(temp != null){
        user = temp;
    }
    return user;
    }

    @Override
    public User getUserByLogin(String login) {
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        String column = "USERNAME";

        if (email.matcher(login).find()) column = "EMAIL";

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
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(user);
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

}
