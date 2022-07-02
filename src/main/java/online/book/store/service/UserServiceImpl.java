package online.book.store.service;

import online.book.store.dao.UserDao;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


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
    public boolean authUser(User userToLogin, User userFromDb) {

        return userToLogin.equals(userFromDb);
    }


    @Override
    public User saveOrGetUser(User user) {
        return this.userDao.saveOrGetUser(user);
    }
}
