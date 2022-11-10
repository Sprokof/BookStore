package online.book.store.service;

import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.entity.UserSession;


public interface SessionService {
    void deleteSession(UserSession session);
    void deleteSessionById(String id);
    boolean sessionExist(String id);
    UserSession getSessionById(String sessionId);
    void updateSession(UserSession userSession);
    SessionDto sessionActive(String sessionId);
    User getCurrentUser(String sessionid);
    SessionDto getSessionData(UserDto userDto);
    void sessionInvalidate(String sessionid);

}
