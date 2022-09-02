package online.book.store.service;

import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface SignInService {
    void addPassword(String hashPassword);
    ResetPasswordDto getResetDto();
    int loginUser(AbstractUserBuilder userBuilder);
    int logout(HttpServletRequest request);
    User getCurrentUser(HttpServletRequest request);
    String getIpAddressFromRequest(HttpServletRequest request);
    public User savedUser();
}
