package online.book.store.controllers;

import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    private User currentUser;

    @Autowired
    @Qualifier("userValidation") Validator userValidator;

    @ModelAttribute("user")
    public UserLoginDto getUser(){
        return new UserLoginDto();
    }

    @ModelAttribute("code")
    public String getCode(){
        return new String();
    }


    @GetMapping("/home/login")
    public String login(){
        return "login";
    }

    @PostMapping("/home/login")
    public String login(@ModelAttribute("user") @Valid UserLoginDto userLoginDto,
                        BindingResult bindingResult){
        userValidator.validate(userLoginDto, bindingResult);
        if(bindingResult.hasErrors()){
            return "login";
        }
        return "code";

    }

    @PostMapping("/home/code")
    public String code(@ModelAttribute("user") UserLoginDto userLoginDto){
      String generatedCode = userLoginDto.getGeneratedCode();
      String codeFromEmail = userLoginDto.getConfirmCode();

      if(generatedCode.equals(codeFromEmail)){
          userLoginDto.setAccepted(true);
          httpSession.setAttribute("user", userService.
                                    saveOrGetUser(userLoginDto.buildUser()));
      }
          userLoginDto.setAccepted(false);
          return "code";
    }

}
