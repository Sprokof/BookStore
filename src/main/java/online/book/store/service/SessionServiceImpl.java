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
    public boolean sessionExist(String id) {
        return this.sessionDao.sessionExist(id);
    }

    @Override
    public UserSession getSessionById(String sessionId) {
        return this.sessionDao.getSessionById(sessionId);
    }

    @Override
    public SessionDto sessionActive(String sessionId) {
        return new SessionDto(getSessionById(sessionId) != null);
    }

    @Override
    public User getCurrentUser(String sessionid) {
        return this.sessionDao.getSessionById(sessionid).getUser();
    }

    @Override
    public SessionDto getSessionData(UserDto userDto) {
        UserSession session = getSessionById(userDto.getSessionid());
        if(session == null) return null;
        String sessionid = session.getSessionId();
        boolean adminSession = session.getUser().isAdmin();
        return new SessionDto(true, sessionid, adminSession);
    }

    @Override
    public void sessionInvalidate(String sessionid) {
        this.sessionDao.deleteSessionById(sessionid);
    }
}
