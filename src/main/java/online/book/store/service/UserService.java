package online.book.store.service;

import online.book.store.entity.User;

import java.util.List;

public interface UserService{
    void saveUser(User user);
    void updateUser(User user);
    User getUserByLogin(String login);
    void updateUserInSession(User user);
    User getUserByIP(String ip);
    void saveOrUpdate(User user);
    User getUserById(int id);
    List<User> getUsersInSession();

}
