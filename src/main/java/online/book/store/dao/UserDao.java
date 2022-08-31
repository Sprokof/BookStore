package online.book.store.dao;


import online.book.store.entity.User;

import java.util.List;

public interface UserDao {
    void saveUser(User user);
    User getUserByLogin(String login);
    void updateUser(User user);
    List<String> allUsernames();
    List<String> allEmails();
    User getUserByIP(String ip);
}
