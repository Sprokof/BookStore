package online.book.store.service;

import online.book.store.dao.UserDao;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private HttpSession httpSession;


    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);

    }

    @Override
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login);
    }

    @Override
    public User loadUserByLogin(String login) {
        User user;
        if ((user = (this.userDao.getUserByLogin(login))) != null) {
            return user;
        }
        return null;
    }

    @Override
    public boolean loginUser() {
      return (httpSession.getAttribute("user") != null);
    }

    @Override
    public User saveOrGetUser(User user) {
        return this.userDao.saveOrGetUser(user);
    }

    @Override
    public User getCurrentUser() {
        return (User) httpSession.getAttribute("user");
    }

    @Override
    public void updateUserInSession(User user){
        httpSession.setAttribute("user", user);
        updateUser(user);
    }
}
