package online.book.store.service;

import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserDto;
import online.book.store.expections.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

public interface SignService {
    void addResetDto(ResetPasswordDto resetPasswordDto);
    ResetPasswordDto getResetDto();
    void loginUser(UserDto userDto);
    HttpStatus logout(UserDto userDto);
    void registration(UserDto userDto);
    boolean adminsRequest(String sessionid);
    String getConfirmationCode();
    void generateNewCode();
    String generateToken(String email);
    void confirmRegistration(String token) throws ResourceNotFoundException;
    void resendConfirmationLink(String login);
    UserDto validateRequest(String sessionid);

}
