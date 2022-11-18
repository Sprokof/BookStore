package online.book.store.service;

import online.book.store.dto.UserDto;
import online.book.store.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService{
    User getUserByLogin(String login);
    void saveOrUpdate(User user);
    void updateUser(User user);
    void saveUser(User user);
    User getUserByToken(String token);
    boolean loginExist(String login);
}
