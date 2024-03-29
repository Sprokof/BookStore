package online.book.store.service;

import cache.LFUCache;
import cache.LFUCacheSingleton;
import online.book.store.dao.UserDaoImpl;
import online.book.store.dao.UserDao;
import online.book.store.entity.Cart;
import online.book.store.entity.User;


import online.book.store.entity.WaitList;
import online.book.store.entity.Wishlist;
import online.book.store.enums.Role;
import online.book.store.hash.SHA256;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class UserServiceImpl implements UserService {
    private final LFUCache cache = LFUCacheSingleton.cacheInstance();


    @Value("${admin.email}")
    private transient String adminEmail;

    private final UserDao userDao = new UserDaoImpl();


    @Override
    public User getUserByLogin(String login) {
       if(cache.keyExist(login)) return (User) cache.get(login);
       User user = this.userDao.getUserByLogin(login);
       cache.put(login, user);
       return user;
    }


    @Override
    public void saveOrUpdate(User user) {
        this.userDao.saveOrUpdate(user);
    }


    @Override
    public boolean updateUser(User user) {
        this.userDao.updateUser(user);
        return true;
    }

    @Override
    public void saveUser(User user) {
        String password = SHA256.hash(user.getPassword());
        user.setPassword(password);
        Role role = defineRole(user.getEmail());
        user.setRole(role);
        user.setWishList(new Wishlist());
        user.setCart(new Cart());
        user.setWaitList(new WaitList());
        this.userDao.saveUser(user);
    }

    @Override
    public User getUserByToken(String token) {
        return this.userDao.getUserByToken(token);
    }

    @Override
    public boolean loginExist(String login) {
        return this.userDao.loginExist(login);
    }

    @Override
    public boolean userAccepted(String login) {
        return this.userDao.userAccept(login);
    }

    private Role defineRole(String email){
        if(email.equals(adminEmail)) return Role.ADMIN;
        return Role.USER;
    }

}


