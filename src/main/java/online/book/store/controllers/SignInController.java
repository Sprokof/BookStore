package online.book.store.controllers;


import online.book.store.dto.ResetPasswordDto;
import online.book.store.dto.UserDto;
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
    public ResponseEntity<?> login(@RequestBody UserDto userDto){
        loginValidation.validation(userDto);
        if(!loginValidation.hasErrors()){
            signInService.loginUser(userDto);
        }
        Map<String, String> errors = loginValidation.validationErrors();
        return ResponseEntity.ok(errors);
    }


    @PostMapping("/home/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto){
        registrationValidation.validation(userDto);
        if(!registrationValidation.hasErrors()){
            signInService.registration(userDto);
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
    public ResponseEntity<Map<String, String>> reset(@RequestBody ResetPasswordDto resetPasswordDto){
        resetValidation.validation(resetPasswordDto);
        if(!resetValidation.hasErrors()){
            signInService.addResetDto(resetPasswordDto);

            String login = resetPasswordDto.getLogin();
            User user = userService.getUserByLogin(login);
            sender.send(user.getEmail(), Subject.RESET_PASSWORD, this.signInService);
        }
        return ResponseEntity.ok(resetValidation.validationErrors());
    }

    @PostMapping("/home/reset/confirm")
    public ResponseEntity<Map<String, String>> confirm(@RequestBody ResetPasswordDto reset){
        ResetPasswordDto resetPasswordDto = signInService.getResetDto();
        String code = reset.getInputCode();
        resetPasswordDto.setInputCode(code);
        confirmValidation.validation(resetPasswordDto);
        if(!confirmValidation.hasErrors()){
            String login = reset.getLogin();
            User user = userService.getUserByLogin(login);
            user.setPassword(resetPasswordDto.getNewPassword());
            userService.saveOrUpdate(user);
        }
    return ResponseEntity.ok(confirmValidation.validationErrors());
    }


    @PostMapping("/home/resend/code")
    public ResponseEntity<Integer> resendCode(@RequestBody ResetPasswordDto dto){
        User user = userService.getUserByLogin(dto.getLogin());
        signInService.generateNewCode();
        sender.send(user.getEmail(), Subject.RESET_PASSWORD, this.signInService);
        return ResponseEntity.ok(200);
    }
}
