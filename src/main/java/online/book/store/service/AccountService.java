package online.book.store.service;

import online.book.store.dto.UserDto;

public interface AccountService {
    int sendNewEmailMessage(UserDto userDto, UserService userService);
    void confirmNewEmail(String email, String token, UserService userService);
}
