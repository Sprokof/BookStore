package online.book.store.dao;

import online.book.store.entity.Notice;
import online.book.store.enums.NoticeStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoticeDaoImpl implements NoticeDao {

    private final SessionFactory sessionFactory = SessionFactoryInitialization.getInitializationFactory();

    @Override
    @SuppressWarnings("unchecked")
    public List<Notice> getAllNewUsersNotices(int userId) {
        Session session = null;
        List<Notice> notices = null;
        try {
            session = this.sessionFactory.openSession();
            session.beginTransaction();
            notices = (List<Notice>) session.createSQLQuery("SELECT * FROM NOTICES WHERE" +
                            " STATUS=:new AND USER_ID=:id").
                    setParameter("new", NoticeStatus.NEW.getStatus()).
                    setParameter("id", userId).addEntity(Notice.class).list();
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
}
