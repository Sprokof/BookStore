package online.book.store.service;

import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface SignInService {
    boolean supports(Class<?> clazz);
    void addPassword(String hashPassword);
    ResetPasswordDto getResetDto();
    int loginUser(Object obj);
    int logout(HttpServletRequest request);
    String getCurrentIP(HttpServletRequest request);
    User getCurrentUser(String ipAddress);
}
