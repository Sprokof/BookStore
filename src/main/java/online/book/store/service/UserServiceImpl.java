package online.book.store.service;

import online.book.store.dao.UserDaoImpl;
import online.book.store.dao.UserDao;
import online.book.store.entity.User;


import online.book.store.hash.SHA256;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Component
public class UserServiceImpl implements UserService{

    @Value("${admin.email}")
    private transient String adminEmail;

    private final UserDao userDao = new UserDaoImpl();

    @Autowired
    private SessionStorage sessionStorage;


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
        user.setAdmin(user.getEmail().equals(adminEmail));

        this.userDao.saveUser(user);
    }


    @Override
    public void updateUserInSession(User user){
        user.setAdmin(user.getEmail().equals(adminEmail));
        sessionStorage.addUser(user);
        updateUser(user);
    }

    @Override
    public User getUserByIP(String ip) {
        return this.userDao.getUserByIP(ip);

    }

    @Override
    public void saveOrUpdate(User user) {
        String password = user.getPassword();
        user.setPassword(SHA256.hash(password));
        user.setAdmin(user.getEmail().equals(adminEmail));
        this.userDao.saveOrUpdate(user);
    }

    @Override
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    @Override
    public List<User> getUsersInSession() {
        return this.userDao.getUsersInSession();
    }
}
