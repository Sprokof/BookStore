package online.book.store.service;

import online.book.store.dao.UserDao;
import online.book.store.entity.User;
import online.book.store.hash.SHA256;

import online.book.store.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private Session httpSession;


    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);

    }

    @Override
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login);
    }

    @Override
    public void saveUser(User user){
        String password = user.getPassword();
        user.setPassword(SHA256.hash(password));
        this.userDao.saveUser(user);
    }


    @Override
    public void updateUserInSession(User user){
        httpSession.addUser(user);
        updateUser(user);
    }

    @Override
    public User getUserByIP(String ip) {
        return this.userDao.getUserByIP(ip);

    }
}
