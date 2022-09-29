package online.book.store.service;

import online.book.store.dao.UserDaoImpl;
import online.book.store.dao.UserDao;
import online.book.store.entity.Cart;
import online.book.store.entity.User;


import online.book.store.entity.Wishlist;
import online.book.store.hash.SHA256;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@Component
public class UserServiceImpl implements UserService{

    @Value("${admin.email}")
    private transient String adminEmail;

    private final UserDao userDao = new UserDaoImpl();

    @Autowired
    private SessionStorage sessionStorage;



    @Override
    public User getUserByLogin(String login) {
        return this.userDao.getUserByLogin(login.replaceAll("\".*\"", ""));
    }


    @Override
    public void updateUserInSession(User user){
        user.setAdmin(user.getEmail().equals(adminEmail));
        sessionStorage.addUser(user);
        saveOrUpdate(user);
    }


    @Override
    public void saveOrUpdate(User user) {
        if (!existUser(user)) {
            String password = SHA256.hash(user.getPassword());
            user.setPassword(password);
            user.setAdmin(user.getEmail().equals(adminEmail));
            user.setWishList(new Wishlist());
            user.setCart(new Cart());
        }
        this.userDao.saveOrUpdate(user);
    }

    @Override
    public User getUserByUUID(String uuid) {
        return this.userDao.getUserByUUID(UUID.fromString(uuid));
    }

    @Override
    public List<User> getUsersInSession() {
        return this.userDao.getUsersInSession();
    }

    private boolean existUser(User user){
        return user.getId() != null;
    }


    private boolean existUUID(UUID uuid) {
        return userDao.existUUID(uuid);
    }


    @Override
    public String generateUUID(){
        UUID uuid = UUID.randomUUID();
        if(existUUID(uuid)) {
            do {
                uuid = UUID.randomUUID();
            } while (existUUID(uuid));
        }
    return uuid.toString();
    }

    @Override
    public String extractValidLogin(String login) {
        if(login.replaceAll("%22%22=", "").isEmpty()){
            return "";
        }
        else {
            login = login.replaceAll("%22", "");
            return login.substring(login.indexOf("=") + 1);
        }
    }
}
