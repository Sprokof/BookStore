package online.book.store.dao;


import online.book.store.entity.User;

public interface UserDao {
    User saveOrGetUser(User user);
    User getUserByLogin(String login);
    void updateUser(User user);
}
