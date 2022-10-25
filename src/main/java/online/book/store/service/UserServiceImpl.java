package online.book.store.service;

import online.book.store.dao.UserDaoImpl;
import online.book.store.dao.UserDao;
import online.book.store.entity.Cart;
import online.book.store.entity.User;


import online.book.store.entity.Wishlist;
import online.book.store.hash.SHA256;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Component
public class UserServiceImpl implements UserService {

    @Value("${admin.email}")
    private transient String adminEmail;

    private final UserDao userDao = new UserDaoImpl();


    @Override
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login.replaceAll("\".*\"", ""));
    }



    @Override
    public void saveOrUpdate(User user) {
        this.userDao.saveOrUpdate(user);
    }


    @Override
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    public void saveUser(User user) {
        String password = SHA256.hash(user.getPassword());
        user.setPassword(password);
        user.setAdmin(user.getEmail().equals(adminEmail));
        user.setWishList(new Wishlist());
        user.setCart(new Cart());
        this.userDao.saveUser(user);
    }

    @Override
    public User getUserByToken(String token) {
        return this.userDao.getUserByToken(token);
    }
}


