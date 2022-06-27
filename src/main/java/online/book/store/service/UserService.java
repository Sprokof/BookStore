package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.User;

public interface UserService {
    void saveUser(User user);
    void updateUser(User user);
    User getUserByLogin(String login);
    User loadUserByLogin(String login);
    boolean authUser(User userToLogin, User userFromDb);
    void addBookToWishList(User currentUser, Book book);
}
