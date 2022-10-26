package online.book.store.service;

import online.book.store.dto.ConfirmDto;
import online.book.store.dto.UserDto;
import online.book.store.expections.ResourceNotFoundException;

public interface SignInService {
    void addResetDto(ConfirmDto confirmDto);
    ConfirmDto getResetDto();
    int loginUser(UserDto userDto);
    int logout(UserDto userDto);
    void registration(UserDto userDto);
    boolean adminsRequest(String sessionid);
    String getConfirmationCode();
    void generateNewCode();
    void autologin(UserDto userDto);
    String generateToken(String email);
    void confirmRegistration(String token) throws ResourceNotFoundException;
    void resendConfirmationLink(String login);
    boolean userAccept(UserDto userDto);
}
