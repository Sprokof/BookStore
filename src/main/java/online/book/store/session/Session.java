package online.book.store.session;

import lombok.NoArgsConstructor;
import online.book.store.entity.User;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.TreeMap;

@Component
@NoArgsConstructor
public class Session {

    private static final HttpSession httpSession = new WiredSession().getHttpSession();

    public void addUser(User user){
        String userIp = user.getIpAddress();
        httpSession.setAttribute(userIp, user);
    }

    public User getUser(String ipAddress){
        return (User) httpSession.getAttribute(ipAddress);
    }

    public void removeUser(User user){
        httpSession.removeAttribute(user.getIpAddress());
    }

    public boolean containsInSession(User user){
        return getUser(user.getIpAddress()) != null;
    }

}
