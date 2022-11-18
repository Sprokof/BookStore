package online.book.store.controllers;

import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.AccountService;
import online.book.store.service.UserService;
import online.book.store.validation.AccountValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidation.EmailValidation emailValidation;

    @Autowired
    private AccountValidation.PasswordValidation passwordValidation;


    @GetMapping("/home/account")
    public String account(@RequestParam("user") String login, Model model) {
        User user = this.userService.getUserByLogin(login);
        model.addAttribute("user", user);
        return "account";
    }

    @GetMapping("/change")
    public String change() {
        return "change";
    }

    @PostMapping("/account/send/new/email")
    public ResponseEntity<Map<String, String>> newEmail (@RequestBody UserDto userDto){
        emailValidation.validation(userDto);
        if(!emailValidation.hasErrors()) {
            accountService.sendNewEmailMessage(userDto, this.userService);
        }
        return ResponseEntity.ok(emailValidation.validationErrors());
    }

    @PostMapping("/account/new/password")
    public ResponseEntity<Map<String, String>> newPassword(@RequestBody UserDto userDto) {
        passwordValidation.validation(userDto);
        if(!passwordValidation.hasErrors()){
            accountService.confirmNewPassword(userDto, this.userService);
        }
    return ResponseEntity.ok(passwordValidation.validationErrors());
    }

    @GetMapping("/bookstore/newemail/confirm")
    public String confirmNewEmail(@RequestParam Map<String, String> params) {
        String email = params.get("email");
        String token = params.get("token");
        if(accountService.emailSet(email, this.userService)) throw new ResourceNotFoundException () ;
        this.accountService.confirmNewEmail(email, token, this.userService);
        return "result";
    }
}