package online.book.store.dao;


import online.book.store.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {
    void saveUser(User user);
    User getUserByLogin(String login);
    void updateUser(User user);
    void saveOrUpdate(User user);
    User getUserByToken(String token);
    boolean loginExist(String login);
}
