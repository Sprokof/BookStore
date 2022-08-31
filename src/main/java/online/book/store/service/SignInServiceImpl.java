package online.book.store.service;

import lombok.Getter;
import lombok.Setter;
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

    @Override
    public int loginUser(Object obj) {
        if (!supports(obj.getClass())) return 501;
        User user = null;
        if (obj instanceof UserDto) {
            user = (((UserDto) obj).buildUser());
            httpSession.addUser(user);

        }
        else if(obj instanceof UserLoginDto) {
            String login = ((UserLoginDto) obj).getLogin();
            user = this.userDao.getUserByLogin(login);
            if (user.isRemembered()) {
                user.setRemembered(true);
                this.userDao.updateUser(user);
            }
            httpSession.addUser(user);
        }
            assert user != null;
            return httpSession.containsInSession(user) ? 200 : 501;
    }

    @Override
    public int logout(HttpServletRequest request){
        String ipAddress = getCurrentIP(request);
        User currentUser = getCurrentUser(ipAddress);
        currentUser.setRemembered(false);
        httpSession.removeUser(currentUser);
        userService.updateUser(currentUser);
        return !httpSession.containsInSession(currentUser) ? 200 : 501;
    }

    @Override
    public String getCurrentIP(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null) {
            ip = request.getRemoteAddr();
        }
    return SHA256.hash(ip);
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

    @Override
    public User getCurrentUser(String ipAddress){
        return httpSession.getUser(ipAddress);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserLoginDto.class) || clazz.equals(UserDto.class);
    }
}
