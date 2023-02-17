package online.book.store.dao;

import online.book.store.entity.Notice;
import online.book.store.enums.NoticeStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class NoticeDaoImpl implements NoticeDao {

    private final SessionFactory sessionFactory = SessionFactoryInitialization.getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<Notice> getAllUsersNotices(int userId) {
        Session session = null;
        List<Notice> notices = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            notices = (List<Notice>) session.
                    createSQLQuery("SELECT * FROM NOTICES WHERE USER_ID=:id AND STATUS !=:old").
                    setParameter("id", userId).
                    setParameter("old", NoticeStatus.OLD.getStatus()).
                    addEntity(Notice.class).list();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
        return notices;
    }

    @Override
    public int getCountNewUsersNotices(int userId) {
        Session session = null;
        BigInteger integer = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            integer = (BigInteger) session.createSQLQuery("SELECT COUNT(ID) FROM NOTICES " +
                            "WHERE STATUS=:new and USER_ID=:id").
                    setParameter("new", NoticeStatus.NEW.getStatus()).
                    setParameter("id", userId).getSingleResult();
            session.getTransaction().rollback();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
                if (e instanceof NoResultException) return 0;
            }
        } finally {
            if (session != null) session.close();
        }
        if (integer == null) return 0;
        return integer.intValue();
    }

    @Override
    public void updateNotice(Notice notice) {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.update(notice);
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
    public Notice getNoticeById(int id) {
        Session session = null;
        Notice notice = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        notice = session.get(Notice.class, id);
        session.getTransaction().rollback();
    }
    catch(Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if(session != null) session.close();
    }
    return notice;
    }

    @Override
    public void setAllReadNoticesToOld() {
        Session session = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        session.createSQLQuery("UPDATE NOTICES SET STATUS=:old " +
                "WHERE cast(TIME_STAMP as date) " +
                "<= cast("+ nowMinusWeek() +" as date)").
                setParameter("old", NoticeStatus.OLD.getStatus()).executeUpdate();
        session.getTransaction().rollback();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
        }
    }
    finally {
        if(session != null) session.close();
    }
    }

    private String nowMinusWeek() {
        LocalDate now = LocalDate.now().minusDays(7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");
        return sdf.format(now);
    }
}
