package online.book.store.controllers;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.service.CategoryService;
import online.book.store.service.SignInService;
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
    private SignInService signInService;


    @Autowired
    private SessionStorage sessionStorage;


    @GetMapping("/instance/popular/categories")
    public ResponseEntity<List<CategoryDto>> popularCategories() {
        List<CategoryDto> categories = categoryService.popularCategories();
        return ResponseEntity.ok(categories);
    }


    @GetMapping("/user/data")
    public ResponseEntity<UserDto> sessionCheck(HttpServletRequest request) {
        UserDto user = sessionStorage.getUserDto(signInService, request);
        return ResponseEntity.ok(user);
    }

    @PostMapping ("/autologin")
    public ResponseEntity<Integer> autoLogin(HttpServletRequest request){
        signInService.autologin(request);
        return ResponseEntity.ok(200);
    }


}





