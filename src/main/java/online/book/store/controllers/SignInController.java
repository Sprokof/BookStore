package online.book.store.controllers;


import online.book.store.dto.ResetDto;
import online.book.store.dto.UserDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.entity.User;
import online.book.store.mail.MailSender;
import online.book.store.mail.Subject;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import online.book.store.validation.AbstractValidation;
import online.book.store.validation.ResetValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class SignInController {


    @Autowired
    private SignInService signInService;

    @Autowired
    private UserService userService;


    @Autowired
    @Qualifier("loginValidation")
    private AbstractValidation loginValidation;

    @Autowired
    @Qualifier("registrationValidation")
    private AbstractValidation registrationValidation;

    @Autowired
    @Qualifier("resetValidation")
    private AbstractValidation resetValidation;

    private final ResetValidation.ConfirmValidation confirmValidation =
            new ResetValidation.ConfirmValidation();

    @Autowired
    private MailSender sender;



    @PostMapping("/home/login")
    public ResponseEntity<?> login(@RequestBody UserDto user){
        loginValidation.validation(user);
        if(!loginValidation.hasErrors()){
            signInService.loginUser(user);
        }
        Map<String, String> errors = loginValidation.validationErrors();
        return ResponseEntity.ok(errors);
    }


    @PostMapping("/home/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto){
        registrationValidation.validation(userDto);
        if(!registrationValidation.hasErrors()){
            signInService.loginUser(userDto);
        }
        Map<String, String> errors = registrationValidation.validationErrors();
        return ResponseEntity.ok(errors);
    }


    @PostMapping("/home/logout")
    public ResponseEntity<Integer> logout(@RequestBody UserDto userDto){
        int code = this.signInService.logout(userDto);
        return ResponseEntity.ok(code);
    }

    @PostMapping("/home/reset")
    public ResponseEntity<Map<String, String>> reset(@RequestBody ResetDto resetDto){
        resetValidation.validation(resetDto);
        if(!resetValidation.hasErrors()){
            signInService.addResetDto(resetDto);

            String login = resetDto.getLogin();
            User user = userService.getUserByLogin(login);
            sender.send(user.getEmail(), Subject.RESET_PASSWORD, this.signInService);
        }
        return ResponseEntity.ok(resetValidation.validationErrors());
    }

    @PostMapping("/home/reset/confirm")
    public ResponseEntity<Map<String, String>> confirm(@RequestBody ResetDto reset){
        ResetDto resetDto = signInService.getResetDto();
        String code = reset.getInputCode();
        resetDto.setInputCode(code);
        confirmValidation.validation(resetDto);
        if(!confirmValidation.hasErrors()){
            String login = reset.getLogin();
            User user = userService.getUserByLogin(login);
            user.setPassword(resetDto.getNewPassword());
            userService.saveOrUpdate(user);
        }
    return ResponseEntity.ok(confirmValidation.validationErrors());
    }


    @PostMapping("/home/resend/code")
    public ResponseEntity<Integer> resendCode(@RequestBody ResetDto resetDto){
        String login = resetDto.getLogin();
        User user = userService.getUserByLogin(login);
        signInService.generateNewCode();
        sender.send(user.getEmail(), Subject.RESET_PASSWORD, this.signInService);
        return ResponseEntity.ok(200);
    }
}
