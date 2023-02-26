package online.book.store.dao;

import online.book.store.config.MailConfig;
import online.book.store.entity.Notice;
import online.book.store.enums.NoticeStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class NoticeDaoImpl implements NoticeDao {

    private final SessionFactory sessionFactory = SessionFactoryInitialization.getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<Notice> getFewUsersNotices(int userId, int count) {
        Session session = null;
        List<Notice> notices = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            notices = (List<Notice>) session.
                    createSQLQuery("SELECT * FROM NOTICES WHERE USER_ID=:id AND STATUS !=:old ORDER BY ID DESC limit(:count)").
                    setParameter("id", userId).
                    setParameter("old", NoticeStatus.OLD.getStatus()).
                    setParameter("count", count).
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
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
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
                                "WHERE cast(DATESTAMP as date) " +
                            "<= cast(:date as date) and STATUS=:read").
                    setParameter("old", NoticeStatus.OLD.getStatus()).
                    setParameter("read", NoticeStatus.READ.getStatus()).
                    setParameter("date", nowMinusDays(7)).
                    executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) session.close();
        }
    }

    private String nowMinusDays(int days) {
        long secondsInDay = 86400L;
        long secondsToMinus = (days * secondsInDay);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "'" + sdf.format(Date.from(Instant.now().
                minusSeconds(secondsToMinus))) + "'";

    }

    @Override
    public int getCountUsersNotices(int userId) {
        Session session = null;
        BigInteger count = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            count = (BigInteger) session.createSQLQuery("SELECT COUNT(ID) " +
                            "FROM NOTICES WHERE USER_ID=:id").
                    setParameter("id", userId).getSingleResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
                if (e instanceof NoResultException) {
                    return 0;
                }
            }
        }
        if (count == null) return 0;
        return count.intValue();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Notice> getAllUsersNotices(String login) {
        String column = defineColumn(login);
        Session session = null;
        List<Notice> notices = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            notices = (List<Notice>) session.
                    createSQLQuery("SELECT * FROM NOTICES " +
                            "as n JOIN USERS as u " +
                            "on n.user_id = u.id " +
                            "WHERE "+ column +"=:login").addEntity(Notice.class).
                    setParameter("login", login).list();
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
    @SuppressWarnings("unchecked")
    public List<Notice> getAllReadNotices(String login) {
        String column = defineColumn(login);
        Session session = null;
        List<Notice> notices = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            notices = (List<Notice>) session.
                    createSQLQuery("SELECT * FROM NOTICES " +
                            "as n JOIN USERS as u " +
                            "on n.user_id = u.id " +
                            "WHERE " + column +"=:login AND STATUS=:read").addEntity(Notice.class).
                    setParameter("login", login).
                    setParameter("read", NoticeStatus.READ.getStatus()).list();
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

    private String defineColumn(String login){
        Pattern email = Pattern.compile(MailConfig.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        if (email.matcher(login).find())  return "EMAIL";
        return "USERNAME";
    }

    @Override
    public boolean noticesExists(String sessionid) {
        Session session = null;
        BigInteger count = null;
    try {
        session = this.sessionFactory.openSession();
        session.beginTransaction();
        count = (BigInteger) session.
                createSQLQuery("SELECT COUNT(n.ID) " +
                        "FROM NOTICES as n JOIN USERS_SESSIONS " +
                        "as us on n.user_id = us.user_id WHERE SESSION_ID=:id").
                setParameter("id", sessionid).getSingleResult();
        session.getTransaction().commit();
    }
    catch (Exception e){
        if(session != null && session.getTransaction() != null){
            session.getTransaction().rollback();
            if(e instanceof NoResultException) return false;
        }
    }
    finally {
        if(session != null) session.close();
    }
        if(count == null) return false;
        return count.intValue() > 0;
    }

    @Override
    public void deleteAllOldNotices() {
        Session session = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM NOTICES " +
                            "WHERE cast(DATESTAMP as date) " +
                            "<= cast(:date as date) and STATUS=:old").
                    setParameter("date", nowMinusDays(10)).
                    setParameter("old", NoticeStatus.OLD.getStatus()).
                    executeUpdate();

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
    }


}
