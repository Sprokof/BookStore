package online.book.store.service;

import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService{
    void saveUser(User user);
    void updateUser(User user);
    User getUserByLogin(String login);
    void updateUserInSession(User user);
    User getUserByIP(String ip);
}
