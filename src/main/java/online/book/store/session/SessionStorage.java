package online.book.store.session;

import lombok.NoArgsConstructor;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import online.book.store.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Signature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor
@Component
public class SessionStorage {

    private static final Map<String, User> sessionStorage = initStorage();


    public void addUser(User user) {
        if (isUserNull(user)) return;
        sessionStorage.putIfAbsent(user.getUserID(), user);
        user.setInSession(true);
    }

    public void removeUser(User user) {
        if (isUserNull(user)) return;
        sessionStorage.remove(user.getUserID());
        user.setInSession(false);

    }

    public boolean containsInSession(UUID uuid) {
        return sessionStorage.containsKey(uuid.toString());
    }


    private static Map<String, User> initStorage(){
        Map<String, User> storage = new HashMap<>();
        List<User> users = new UserServiceImpl().getUsersInSession();
        if(users != null && !users.isEmpty()){
            users.forEach((user) -> {
                    String id = user.getUserID();
                    storage.putIfAbsent(id, user);
            });
        }
    return storage;
    }

    private boolean isUserNull(User user){
        return user == null;
    }


    public SessionDto validateSession(User user, HttpServletRequest request){
        UUID uuid = null;
        if((uuid = getUUID(user, request)) == null) return null;
        boolean active = containsInSession(uuid) && user.isInSession();
        return new SessionDto(user.getEmail(), user.isAdmin(), active);

    }

    private UUID getUUID(User user, HttpServletRequest request){
        if(user == null) return null;
        String uuid = (String) request.getSession().
                getAttribute("id");
        if(uuid == null){
            uuid = user.getUserID();
            request.getSession().setAttribute("id", uuid.toString());
        }
    return UUID.fromString(uuid);
    }

    public User getUser(HttpServletRequest request){
        HttpSession httpSession = request.getSession(false);
        String uuid = (String) httpSession.getAttribute("id");
        return sessionStorage.get(uuid);
    }
}
