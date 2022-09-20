package online.book.store.controllers;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.service.CategoryService;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class StoreController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SessionStorage sessionStorage;

    @Autowired
    private UserService userService;

    @Autowired
    private SignInService signInService;



    @GetMapping("/instance/popular/categories")
    public ResponseEntity<List<CategoryDto>> popularCategories() {
        List<CategoryDto> categories = categoryService.popularCategories();
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/session/validate")
    public ResponseEntity<SessionDto> validateSession(@RequestBody UserDto userDto, HttpServletRequest request) {
        User user = userService.getUserByLogin(userDto.getLogin());
        return ResponseEntity.ok(sessionStorage.validateSession(user, request));
    }


    @PostMapping("/session/out")
    public ResponseEntity<Integer> out(@RequestBody UserDto userDto){
        signInService.logout(userDto.getLogin());
        return ResponseEntity.ok(200);
    }

    @PostMapping("/autologin")
    public ResponseEntity<Integer> autologin(@RequestBody UserDto userDto, HttpServletRequest request){
        signInService.autologin(userDto.getLogin(), request);
        return ResponseEntity.ok(200);
    }

}





