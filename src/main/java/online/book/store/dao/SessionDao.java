package online.book.store.dao;


import online.book.store.entity.UserSession;

public interface SessionDao {
    void deleteSession(UserSession session);
    void deleteSessionById(String id);
    boolean sessionExist(String id);
    UserSession getSessionById(String sessionId);
    boolean sessionFirst(String sessionid);
}
