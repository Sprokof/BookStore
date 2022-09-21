package online.book.store.service;

import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dto.ResetDto;
import online.book.store.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface SignInService {
    void addResetDto(ResetDto resetDto);
    ResetDto getResetDto();
    int loginUser(HttpServletRequest request, AbstractUserBuilder userBuilder);
    int logout(HttpServletRequest request);
    User getCurrentUser(HttpServletRequest request);
    User getSavedUser();
    User getUserFromRequest(HttpServletRequest request);
    boolean adminsRequest(HttpServletRequest request);
    String getConfirmationCode();
    void generateNewCode();
    void autologin(String login, HttpServletRequest request);
    SignInService logout(String login);
    void invalidate(HttpServletRequest request);
}
