package online.book.store.service;

import lombok.NoArgsConstructor;
import online.book.store.dto.ConfirmDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.entity.UserSession;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.hash.SHA256;
import online.book.store.mail.MailSender;
import online.book.store.mail.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@NoArgsConstructor
public class SignInServiceImpl implements SignInService {

    @Autowired
    private UserService userService;

    private ConfirmDto passwordDto;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private MailSender sender;

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
    public void addResetDto(ConfirmDto confirmDto) {
        String password = confirmDto.getNewPassword();
        confirmDto.setNewPassword(SHA256.hash(password));
        this.passwordDto = confirmDto;
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
    public ConfirmDto getResetDto() {
        return this.passwordDto;
    }


    @Override
    public boolean adminsRequest(String sessionid) {
        if (!this.sessionService.sessionActive(sessionid).isActive()) return false;
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


    private void initUserSession(UserDto userDto) {
        String sessionId = userDto.getSessionid();
        User user = getUser(userDto);
        UserSession session = null;
        if (!this.sessionService.sessionExist(sessionId)) {
            session = new UserSession(sessionId);
            user.addSession(session);
            this.userService.updateUser(user);
        } else {
            session = this.sessionService.getSessionById(sessionId);
            this.sessionService.updateSession(session, true);

        }

    }

    private User getUser(UserDto userDto) {
        String login = userDto.getLogin();
        return this.userService.getUserByLogin(login);
    }

    @Override
    public void registration(UserDto userDto) {
        User user = userDto.doUserBuilder();
        userService.saveUser(user);
        sender.send(user.getEmail(), Subject.CONFIRM_REGISTRATION, this);
    }

    @Override
    public String generateToken(String email) {
        return SHA256.hash(email);
    }

    @Override
    public void confirmRegistration(String token) throws ResourceNotFoundException{
        User user = this.userService.getUserByToken(token);
        if(user != null){
            if(user.isAccepted()) throw new ResourceNotFoundException ();
            user.setAccepted(true);
            this.userService.updateUser(user);
        }
    }

    @Override
    public void resendConfirmationLink(String login) {
        User user = this.userService.getUserByLogin(login);
        String email = user.getEmail();
        sender.send(email, Subject.CONFIRM_REGISTRATION, this);
    }
}


