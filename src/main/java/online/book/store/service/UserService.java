package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.User;

public interface UserService {
    User saveOrGetUser(User user);
    void updateUser(User user);
    User getUserByLogin(String login);
    User loadUserByLogin(String login);
    boolean loginUser();

}
