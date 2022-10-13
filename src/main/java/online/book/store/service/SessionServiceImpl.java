package online.book.store.service;

import online.book.store.dao.SessionDao;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionServiceImpl implements SessionService{

    @Autowired
    private SessionDao sessionDao;

    @Override
    public void deleteSession(UserSession session) {
        this.sessionDao.deleteSession(session);
    }

    @Override
    public void deleteSessionById(String id) {
        this.sessionDao.deleteSessionById(id);
    }

    @Override
    public boolean sessionExist(String id) {
        return this.sessionDao.sessionExist(id);
    }

    @Override
    public UserSession getSessionById(String sessionId) {
        return this.sessionDao.getSessionById(sessionId);
    }


    @Override
    public void updateSession(UserSession userSession, boolean active) {
        userSession.setActive(active);
        this.sessionDao.updateSession(userSession);
    }

    @Override
    public SessionDto sessionActive(String sessionId) {
        if(this.sessionDao.getSessionById(sessionId) == null) return new SessionDto(false);
        boolean active = this.sessionDao.getSessionById(sessionId).isActive();
        return new SessionDto(active);
    }

    @Override
    public User getCurrentUser(String sessionid) {
        return this.sessionDao.getSessionById(sessionid).getUser();
    }

    @Override
    public SessionDto getSessionData(UserDto userDto) {
        UserSession session = getSessionById(userDto.getSessionid());
        if(session == null) return null;
        boolean active = session.isActive();
        String sessionid = session.getSessionId();
        boolean adminSession = session.getUser().isAdmin();
        return new SessionDto(active, sessionid, adminSession);
    }

    @Override
    public void sessionInvalidate(SessionDto sessionDto) {
        String sessionId = sessionDto.getSessionid();
        this.sessionDao.deleteSessionById(sessionId);
    }
}
