package online.book.store.service;

import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dto.ResetDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface SignInService {
    void addResetDto(ResetDto resetDto);
    ResetDto getResetDto();
    int loginUser(UserDto userDto);
    int logout(UserDto userDto);
    boolean adminsRequest(String sessionid);
    String getConfirmationCode();
    void generateNewCode();
    void autologin(UserDto userDto);
}
