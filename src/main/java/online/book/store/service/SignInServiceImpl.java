package online.book.store.service;

import lombok.NoArgsConstructor;
import online.book.store.builder.AbstractUserBuilder;
import online.book.store.dto.ResetDto;
import online.book.store.dto.UserDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;
import online.book.store.entity.UserSession;
import online.book.store.hash.SHA256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Component
@NoArgsConstructor
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserService userService;

    private ResetDto passwordDto;

    @Autowired
    private SessionService sessionService;

    @Override
    public int loginUser(UserDto userDto) {
        initUserSession(userDto);
        String sessionid = userDto.getSessionid();
        boolean active = sessionService.sessionActive(sessionid).isActive();
        return active ? 200 : 501;

    }


    @Override
    public int logout(UserDto userDto) {
        String sessionid = userDto.getSessionid();
        UserSession userSession = this.sessionService.getSessionById(sessionid);
        this.sessionService.updateSession(userSession, false);
        boolean active = sessionService.sessionActive(sessionid).isActive();
        return !active ? 200 : 501;
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


    @Override
    public boolean adminsRequest(String sessionid) {
        if(!this.sessionService.sessionActive(sessionid).isActive()) return false;
        return this.sessionService.getCurrentUser(sessionid).isAdmin();
    }

    @Override
    public void generateNewCode() {
        this.passwordDto.setGeneratedCode(generateCode());
    }

    @Override
    public String getConfirmationCode() {
        return getResetDto().getGeneratedCode();
    }



    @Override
    public void autologin(UserDto userDto) {
        String sessionid = userDto.getSessionid();
        User user = getUser(userDto);
        user.addSession(new UserSession(sessionid));
        this.userService.saveOrUpdate(user);
    }


    private void initUserSession(UserDto userDto){
        String sessionId = userDto.getSessionid();
        User user = getUser(userDto);
        UserSession session = null;
        if(!this.sessionService.sessionExist(sessionId)){
            session = new UserSession(sessionId);
            user.addSession(session);
            this.userService.saveOrUpdate(user);
        }
        else {
            session = this.sessionService.getSessionById(sessionId);
            this.sessionService.updateSession(session, true);

        }

    }
    private User getUser(UserDto userDto){
        String login = userDto.getLogin();
        if(login != null){
            return this.userService.getUserByLogin(login);
        }
        return userDto.doUserBuilder();
    }

}


