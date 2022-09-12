package online.book.store.controllers;


import online.book.store.dto.UserLoginDto;
import online.book.store.dto.UserSignInDto;
import online.book.store.entity.User;
import online.book.store.mail.MailSender;
import online.book.store.mail.MailSubjects;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import online.book.store.validation.AbstractValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class SignInController {


    @Autowired
    private SignInService signInService;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("loginValidation")
    AbstractValidation loginValidation;

    @Autowired
    @Qualifier("registrationValidation")
    AbstractValidation registrationValidation;

    @Autowired
    private MailSender sender;



    @PostMapping("/home/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto user, HttpServletResponse response,
                                   HttpServletRequest request){
        loginValidation.validation(user);
        if(!loginValidation.hasErrors()){
            user.setIpAddress(signInService.getIpAddressFromRequest(request));
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
        return ResponseEntity.ok(code);
    }

    @PostMapping("/home/reset")
    public ResponseEntity.BodyBuilder reset(@RequestBody String newPassword,
                                            HttpServletRequest httpServletRequest){
        signInService.addPassword(newPassword);
        User currentUser = signInService.getCurrentUser(httpServletRequest);
        sender.send(currentUser.getEmail(), MailSubjects.RESET_PASSWORD,
                signInService.getResetDto().getConfirmCode());
        return ResponseEntity.status(200);
    }

    @PostMapping("/home/reset/confirm")
    public ResponseEntity<?> confirm(@RequestBody String code, HttpServletRequest httpServletRequest){
        boolean correct = false;
        if(code.equals(signInService.getResetDto().getConfirmCode())){
            correct = true;
            User currentUser = signInService.getCurrentUser(httpServletRequest);
            currentUser.setPassword(signInService.getResetDto().getPassword());
            userService.updateUserInSession(currentUser);
        }
    return ResponseEntity.ok(correct);
    }
}
