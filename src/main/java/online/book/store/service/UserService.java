package online.book.store.service;

import online.book.store.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService{
    User getUserByLogin(String login);
    void updateUserInSession(User user);
    void saveOrUpdate(User user);
    User getUserByUUID(String uuid);
    List<User> getUsersInSession();
    String extractValidLogin(String login);
}
