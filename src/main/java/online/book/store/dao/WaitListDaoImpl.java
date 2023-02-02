package online.book.store.dao;

import online.book.store.entity.WaitList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;

@Component
public class WaitListDaoImpl implements WaitListDao {

    private final SessionFactory sessionFactory =
            SessionFactoryInitialization.getInitializationFactory();

    @Override
    public boolean contains(int bookId, WaitList waitList) {
        Session session = null;
        Integer id = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        id = (Integer) session.createSQLQuery("SELECT book_id FROM BOOKS_WAITS_LISTS " +
                "WHERE book_id=:b_id and wait_list_id=:wl_id").getSingleResult();
        session.getTransaction().commit();
    }
        catch (Exception e){
            if(session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
                if(e instanceof NoResultException) return false;
            }
        }
    finally {
        if(session != null) session.close();
    }
    return id != null;
    }

    @Override
    public void updateWaitList(WaitList waitList) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.update(waitList);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public WaitList saveWaitList(WaitList waitList) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.save(waitList);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }

        int userId = waitList.getUserId();
        return getWaitListByUserId(userId);
    }

    private WaitList getWaitListByUserId(int userId) {
        Session session = null;
        WaitList waitList = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            waitList = (WaitList) session.createSQLQuery("SELECT * FROM WAITS_LISTS " +
                            "WHERE user_id=:id").addEntity(WaitList.class).
                    setParameter("id", userId).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
        return waitList;
    }
}
