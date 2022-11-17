package online.book.store.controllers;

import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.service.AccountService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Integer> newEmail (@RequestBody UserDto userDto){
        int code = this.accountService.sendNewEmailMessage(userDto, this.userService);
        return ResponseEntity.ok(code);
    }

    @PostMapping("http://localhost:8080/bookstore/email/confirm")
    public String confirmNewEmail(@RequestParam Map<String, String> params) {
        String email = params.get("email");
        String token = params.get("token");
        this.accountService.confirmNewEmail(email, token, this.userService);
        return "result";
    }
}
