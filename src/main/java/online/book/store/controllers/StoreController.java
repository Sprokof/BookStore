package online.book.store.controllers;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.ResponseDto;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.ExistCategory;
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
import java.util.Arrays;
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



    @GetMapping("/categories")
    public ResponseEntity<List<ExistCategory>> popularCategories() {
        List<ExistCategory> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/session/validate")
    public ResponseEntity<ResponseDto> validateSession(@RequestBody UserDto userDto, HttpServletRequest request) {
        User user = userService.getUserByLogin(userDto.getLogin());
        return ResponseEntity.ok(sessionStorage.validateSession(user, request));
    }


    @PostMapping(value = "/invalidate")
    public ResponseEntity<Integer> out(@RequestBody String user, HttpServletRequest request){
        signInService.logout(user).invalidate(request);
        return ResponseEntity.ok(200);
    }

    @PostMapping(value = "/autologin")
    public ResponseEntity<Integer> autologin(@RequestBody String user, HttpServletRequest request){
        signInService.autologin(user, request);
        return ResponseEntity.ok(200);
    }

}





