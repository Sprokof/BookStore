package online.book.store.service;

import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;
import online.book.store.hash.SHA256;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@Component
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SessionStorage sessionStorage;

    @Autowired
    private UserService userService;

    private ResetPasswordDto passwordDto;

    private User user;


    public SignInServiceImpl () {
    }

    @Override
    public int loginUser(HttpServletRequest request,
                         AbstractUserBuilder userBuilder) {
        User user;
        if (userBuilder instanceof UserLoginDto) {
            user = this.userService.getUserByLogin(userBuilder.getLogin());
            UserLoginDto userLoginDto = (UserLoginDto) userBuilder;
            user.setRemembered(userLoginDto.isRemembered());
        }

        else {
            user = ((UserSignInDto) userBuilder).doUserBuilder();
            String ipAddress = getIpAddressFromRequest(request);
            user.setIpAddress(ipAddress);
        }
        sessionStorage.addUser(user);
        userService.saveOrUpdate(user);
        return sessionStorage.containsInSession(user) ? 200 : 501;

    }


    @Override
    public int logout(HttpServletRequest request) {
        String ipAddress = getIpAddressFromRequest(request);
        User user = userService.getUserByIP(ipAddress);
        sessionStorage.removeUser(user);
        userService.updateUser(user);
        return !sessionStorage.containsInSession(user) ? 200 : 501;
    }


    @Override
    public void addPassword(String hashPassword) {
        if (this.passwordDto == null) this.passwordDto = new ResetPasswordDto();
        this.passwordDto.setPassword(hashPassword);
        this.passwordDto.setConfirmCode(generateCode());
    }

    private String generateCode() {
        StringBuilder generatedCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            double randomNum = (Math.random() * 9);
            generatedCode.append((int) randomNum);
        }
        return generatedCode.toString();
    }

    @Override
    public ResetPasswordDto getResetDto() {
        return this.passwordDto;
    }

    public User getCurrentUser(HttpServletRequest request) {
        this.user = getUserFromRequest(request);
        return this.user;
    }

    @Override
    public String getIpAddressFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return SHA256.hash(ip);
    }



    @Override
    public void autologin(HttpServletRequest request) {
        User user = getUserFromRequest(request);
        if (user.isInSession()) logout(request);
        if (user.isRemembered()) userService.updateUserInSession(user);

    }

    @Override
    public User getSavedUser() {
        return this.user;
    }

    @Override
    public User getUserFromRequest(HttpServletRequest request) {
        String ipAddress = getIpAddressFromRequest(request);
        return userService.getUserByIP(ipAddress);
    }
}
