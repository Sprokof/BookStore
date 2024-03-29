package online.book.store.service;

import lombok.NoArgsConstructor;
import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.entity.UserSession;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.hash.SHA256;
import online.book.store.mail.MailSender;
import online.book.store.mail.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@NoArgsConstructor
public class SignServiceImpl implements SignService {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionStatisticsService statisticsService;

    @Autowired
    private MailSender sender;

    private ResetPasswordDto passwordDto;

    @Override
    public void loginUser(UserDto userDto) {
        initSession(userDto);
    }

    @Override
    public HttpStatus logout(UserDto userDto) {
        String sessionid = userDto.getSessionid();
        this.sessionService.sessionInvalidate(sessionid);
        boolean active = sessionService.sessionActive(sessionid);
        if(!active) return HttpStatus.ACCEPTED;
        return HttpStatus.CONFLICT;
    }


    @Override
    public void addResetDto(ResetPasswordDto resetPasswordDto) {
        String password = resetPasswordDto.getNewPassword();
        resetPasswordDto.setNewPassword(SHA256.hash(password));
        this.passwordDto = resetPasswordDto;
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
    public ResetPasswordDto getResetDto() {
        return this.passwordDto;
    }


    @Override
    public boolean adminsRequest(String sessionid) {
        if (!this.sessionService.sessionExist(sessionid)) return false;
        UserSession userSession = this.sessionService.getSessionById(sessionid);
        return this.sessionService.adminSession(userSession);
    }

    @Override
    public void generateNewCode() {
        this.passwordDto.setGeneratedCode(generateCode());
    }

    @Override
    public String getConfirmationCode() {
        return getResetDto().getGeneratedCode();
    }



    private void initSession(UserDto userDto) {
        String sessionid = userDto.getSessionid();
        User user = getUser(userDto);
        user.addSession(new UserSession(sessionid, this.statisticsService.statistics()));
        if(this.userService.updateUser(user)) {
            boolean uniqueUserSession = this.sessionService.uniqueUserSession(sessionid);
            this.statisticsService.incrementActiveSession(uniqueUserSession);
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


    @Override
    public UserDto validateRequest(String sessionid) {
        User user = this.sessionService.getCurrentUser(sessionid, true);
        if(user != null) return new UserDto(user.getUsername(), user.getEmail());
        return null;
    }
}


