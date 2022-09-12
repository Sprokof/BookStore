package online.book.store.session;

import lombok.NoArgsConstructor;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.service.SignInService;
import online.book.store.service.UserServiceImpl;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Signature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Component
public class SessionStorage {

    private static final Map<String, User> sessionStorage = initStorage();


    public void addUser(User user) {
        if (isUserNull(user)) return;
        sessionStorage.putIfAbsent(user.getIpAddress(), user);
        user.setInSession(true);
    }

    public void removeUser(User user) {
        if (isUserNull(user)) return;
        sessionStorage.remove(user.getIpAddress());
        user.setInSession(false);
    }

    public boolean containsInSession(User user) {
        if (isUserNull(user)) return false;
        return sessionStorage.containsKey(user.getIpAddress());
    }

    public UserDto getUserDto(SignInService signInService, HttpServletRequest request) {
        User user = signInService.getUserFromRequest(request);
        if(user != null){
            return new UserDto(String.valueOf(user.isAdmin()),
                    containsInSession(user));
        }
        return new UserDto("false", false);
    }


    private static Map<String, User> initStorage(){
        Map<String, User> storage = new HashMap<>();
        List<User> users = new UserServiceImpl().getUsersInSession();
        if(!users.isEmpty()){
            users.forEach((user) -> {
                String ipAddress = user.getIpAddress();
                storage.putIfAbsent(ipAddress, user);
            });
        }
    return storage;
    }

    private boolean isUserNull(User user){
        return user == null;
    }
}
