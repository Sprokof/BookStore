package online.book.store.controllers;

import online.book.store.dto.UserLoginDto;
import online.book.store.entity.User;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

// must be modal window
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserLoginDto getUser(){
        return new UserLoginDto();
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("user") UserLoginDto userLoginDto, HttpServletRequest request){

        User userToLogin = userLoginDto.doUserBuilder();
        String username = request.getParameter("user");
        User userFromDb = userService.loadUserByLogin(username);

        if(userService.authUser(userToLogin, userFromDb)){

            if((userToLogin = (User) request.getSession().getAttribute("user")) == null){
                username = request.getParameter("user");
                userToLogin = userService.getUserByLogin(username);
                request.getSession().setAttribute("user", userToLogin);
            }

            return "k";
    }
        else {

            return "login";
        }

    }
}
