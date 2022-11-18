package online.book.store.service;

import online.book.store.dto.UserDto;

public interface AccountService {
    void sendNewEmailMessage(UserDto userDto, UserService userService);
    void confirmNewEmail(String email, String token, UserService userService);
    void confirmNewPassword(UserDto userDto, UserService userService);
    boolean emailSet(String email, UserService userService);
}
