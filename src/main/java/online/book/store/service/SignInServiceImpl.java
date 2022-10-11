package online.book.store.service;

import lombok.NoArgsConstructor;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dto.ResetDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;
import online.book.store.hash.SHA256;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
@Component
@NoArgsConstructor
public class SignInServiceImpl implements SignInService {

    private static final int INACTIVE_TIME = 540000;

    @Autowired
    private SessionStorage sessionStorage;

    @Autowired
    private UserService userService;

    private ResetDto passwordDto;

    private User user;

    @Override
    public int loginUser(HttpServletRequest request,
                         AbstractUserBuilder userBuilder) {
        User user;
        String uuid;
        if (userBuilder instanceof UserLoginDto) {
            user = this.userService.getUserByLogin(userBuilder.getLogin());
            uuid = user.getUserID();
        }

        else {
            user = ((UserSignInDto) userBuilder).doUserBuilder();
            uuid = this.userService.generateUUID();
            user.setUserID(uuid);
        }
        addToSession(request, uuid);
        sessionStorage.addUser(user);
        userService.saveOrUpdate(user);
        return sessionStorage.containsInSession(UUID.fromString(uuid)) ? 200 : 501;

    }


    @Override
    public int logout(HttpServletRequest request) {
        User user = getUserFromRequest(request);
        sessionStorage.removeUser(user);
        invalidate(request);
        userService.saveOrUpdate(user);
        return !sessionStorage.containsInSession(UUID.fromString(user.getUserID())) ? 200 : 501;
    }


    @Override
    public void addResetDto(ResetDto resetDto) {
        String password = resetDto.getNewPassword();
        resetDto.setNewPassword(SHA256.hash(password));
        this.passwordDto = resetDto;
        this.passwordDto.setGeneratedCode(generateCode());
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
    public ResetDto getResetDto() {
        return this.passwordDto;
    }

    public User getCurrentUser(HttpServletRequest request) {
        this.user = getUserFromRequest(request);
        return this.user;
    }


    @Override
    public User getSavedUser() {
        return this.user;
    }

    @Override
    public User getUserFromRequest(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        String uuid = (String) session.getAttribute("id");
        if(uuid == null) return null;
        this.user = userService.getUserByUUID(uuid);
        return this.user;
    }

    @Override
    public boolean adminsRequest(HttpServletRequest request) {
        User user = getUserFromRequest(request);
        if(user == null || !user.isInSession()) return false;
        return user.isAdmin();
    }

    @Override
    public void generateNewCode() {
        this.passwordDto.setGeneratedCode(generateCode());
    }

    @Override
    public String getConfirmationCode() {
        return getResetDto().getGeneratedCode();
    }


    private void loginUser(User user) {
        sessionStorage.addUser(user);
        userService.saveOrUpdate(user);
    }

    @Override
    public void autologin(String login, HttpServletRequest request) {
        User user = userService.getUserByLogin(login);
        loginUser(user);
        request.getSession().setAttribute("id", user.getUserID());
    }

    @Override
    public SignInService logout(String login) {
        User user = userService.getUserByLogin(login);
        user.setInSession(false);
        userService.saveOrUpdate(user);
        return this;
    }

    @Override
    public void invalidate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.isNew()) return ;
        session.invalidate();
    }


    private void addToSession(HttpServletRequest request, String uuid){
        HttpSession session = request.getSession();
        if(session.isNew()) return ;
        session.setMaxInactiveInterval(INACTIVE_TIME);
        session.setAttribute("id", uuid);
    }
}
