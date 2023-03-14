package online.book.store.service;

import cache.LFUCache;
import cache.LFUCacheSingleton;
import online.book.store.dao.SessionDao;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.entity.UserSession;
import online.book.store.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SessionServiceImpl implements SessionService{

    private final LFUCache cache = LFUCacheSingleton.cacheInstance();

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private SessionStatisticsService statService;

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
    public boolean sessionActive(String sessionId) {
        return sessionExist(sessionId);
    }

    @Override
    public User getCurrentUser(String sessionid, boolean useCache) {
        if(!sessionExist(sessionid)) return null;
        if(useCache && this.cache.keyExist(sessionid)) {
            return (User) this.cache.get(sessionid);
        }
        return this.sessionDao.getSessionById(sessionid).getUser();
    }

    @Override
    public SessionDto getSessionData(UserDto userDto) {
        UserSession session = getSessionById(userDto.getSessionid());
        if(session == null) return new SessionDto(false);
        boolean adminSession = adminSession(session);
        return new SessionDto(true, adminSession);
    }

    @Override
    public void sessionInvalidate(String sessionid) {
        boolean userSession = uniqueUserSession(sessionid);
        statService.decrementActiveSession(userSession);
        deleteSession(sessionid);
    }

    public boolean adminSession(UserSession userSession){
        Role userRole = userSession.getUser().getRole();
        return userRole.equals(Role.ADMIN);
    }


    @Override
    public boolean uniqueUserSession(String sessionid) {
        return this.sessionDao.uniqueUserSession(sessionid);
    }

    private void deleteSession(String sessionid){
        if(this.cache.keyExist(sessionid)) this.cache.remove(sessionid);
        this.sessionDao.deleteSessionById(sessionid);
    }
}
