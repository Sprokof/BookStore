package online.book.store.session;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;

@Getter
public class WiredSession {
    @Autowired
    private HttpSession httpSession;
}
