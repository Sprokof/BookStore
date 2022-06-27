package online.book.store.dao;


import online.book.store.entity.User;

public interface UserDao {
    void saveUser(User user);
    User getUserByLogin(String login);
    void updateUser(User user);
}
