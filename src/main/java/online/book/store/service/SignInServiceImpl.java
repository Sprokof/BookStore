package online.book.store.service;

import lombok.Getter;
import lombok.Setter;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dao.UserDao;
import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;
import online.book.store.hash.SHA256;
import online.book.store.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class SignInServiceImpl implements SignInService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private Session httpSession;

    @Autowired
    private UserService userService;

    private ResetPasswordDto passwordDto;

    private User user;

    @Override
    public int loginUser(AbstractUserBuilder userBuilder) {
        User user = this.userDao.getUserByLogin(userBuilder.getLogin());
        if(userBuilder instanceof UserLoginDto) {
            UserLoginDto userLoginDto = (UserLoginDto) userBuilder;
            if (userLoginDto.isRemembered()) {
                user.setRemembered(true);
            }
        }

        this.userDao.updateUser(user);
        httpSession.addUser(user);
        return httpSession.containsInSession(user) ? 200 : 501;

    }


    @Override
    public int logout(HttpServletRequest request){
        User currentUser = getCurrentUser(request);
        currentUser.setRemembered(false);
        httpSession.removeUser(currentUser);
        userService.updateUser(currentUser);
        return !httpSession.containsInSession(currentUser) ? 200 : 501;
    }


    @Override
    public void addPassword(String hashPassword) {
        if(this.passwordDto == null) this.passwordDto = new ResetPasswordDto();
        this.passwordDto.setPassword(hashPassword);
        this.passwordDto.setConfirmCode(generateCode());
    }

    private String generateCode(){
        StringBuilder generatedCode = new StringBuilder();
        for(int i = 0; i < 6; i ++){
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
        this.user = httpSession.getUser(getIpAddressFromRequest(request));
        return this.user;
    }

    @Override
    public String getIpAddressFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null) {
            ip = request.getRemoteAddr();
        }
        return SHA256.hash(ip);
    }

    @Override
    public User savedUser() {
        return this.user;
    }

}
