package online.book.store.service;

import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.hash.SHA256;
import online.book.store.mail.MailSender;
import online.book.store.mail.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService{

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SignService signService;

    @Override
    public void sendNewEmailMessage(UserDto userDto, UserService userService) {
        String newEmail = userDto.getEmail();
        String sessionid = userDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid, true);
        String newToken = this.signService.generateToken(newEmail);
        user.setToken(newToken);
        userService.updateUser(user);
        mailSender.send(newEmail, Subject.CONFIRM_NEW_EMAIL, this.signService);
    }

    @Override
    public void confirmNewEmail(String email, String token, UserService userService) {
        User user = userService.getUserByToken(token);
        user.setEmail(email);
        userService.updateUser(user);
    }

    @Override
    public void confirmNewPassword(UserDto userDto, UserService userService) {
        String sessionid = userDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid, false);
        String password = user.getPassword();
        user.setPassword(SHA256.hash(password));
        userService.updateUser(user);

    }

    @Override
    public boolean emailSet(String email, UserService userService) {
        return userService.loginExist(email);
    }

    @Override
    public void confirmNewUsername(UserDto userDto, UserService userService) {
        String sessionid = userDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid, false);
        user.setUsername(userDto.getUsername());
        userService.updateUser(user);
    }
}
