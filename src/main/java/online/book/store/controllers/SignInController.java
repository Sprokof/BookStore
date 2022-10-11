package online.book.store.controllers;


import online.book.store.dto.ResetDto;
import online.book.store.dto.UserLoginDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.entity.User;
import online.book.store.mail.MailSender;
import online.book.store.mail.Subject;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import online.book.store.session.SessionStorage;
import online.book.store.validation.AbstractValidation;
import online.book.store.validation.ResetValidation;
import online.book.store.validation.ValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    public ResponseEntity<?> login(@RequestBody UserLoginDto user, HttpServletRequest request){
        loginValidation.validation(user);
        if(!loginValidation.hasErrors()){
            signInService.loginUser(request, user);
        }
        Map<String, String> errors = loginValidation.validationErrors();
        return ResponseEntity.ok(errors);
    }


    @PostMapping("/home/registration")
    public ResponseEntity<?> registration(@RequestBody UserSignInDto userSignInDto,
                                          HttpServletRequest request){
        registrationValidation.validation(userSignInDto);
        if(!registrationValidation.hasErrors()){
            signInService.loginUser(request, userSignInDto);
        }
        Map<String, String> errors = registrationValidation.validationErrors();
        return ResponseEntity.ok(errors);
    }


    @PostMapping("/home/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String code = (String.valueOf(signInService.logout(request)));
        request.getSession().invalidate();
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
    public ResponseEntity<Map<String, String>> confirm(@RequestBody String code, HttpServletRequest httpServletRequest){
        ResetDto resetDto = signInService.getResetDto();
        resetDto.setInputCode(code);
        confirmValidation.validation(resetDto);
        if(!confirmValidation.hasErrors()){
            User user = signInService.getUserFromRequest(httpServletRequest);
            user.setPassword(resetDto.getNewPassword());
            userService.saveOrUpdate(user);
        }
    return ResponseEntity.ok(confirmValidation.validationErrors());
    }


    @PostMapping("/home/resend/code")
    public ResponseEntity<Integer> resendCode(HttpServletRequest request){
        User user = signInService.getCurrentUser(request);
        signInService.generateNewCode();
        sender.send(user.getEmail(), Subject.RESET_PASSWORD, this.signInService);
        return ResponseEntity.ok(200);
    }
}
