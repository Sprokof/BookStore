package online.book.store.controllers;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.User;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.CategoryService;
import online.book.store.service.SessionService;
import online.book.store.service.SignInService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
public class StoreController{

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SignInService signInService;

    @Autowired
    private SessionService sessionService;


    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> popularCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/session/validate")
    public ResponseEntity<SessionDto> validateSession(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(sessionService.getSessionData(userDto));
    }


    @PostMapping(value = "/invalidate")
    public ResponseEntity<Integer> invalidate(@RequestBody SessionDto sessionDto){
        String sessionid = sessionDto.getSessionid();
        this.sessionService.sessionInvalidate(sessionid);
        return ResponseEntity.ok(200);
    }

    @PostMapping(value = "/autologin")
    public ResponseEntity<Integer> autologin(@RequestBody UserDto userDto){
        this.signInService.autologin(userDto);
        return ResponseEntity.ok(200);
    }


    @PostMapping("/session/active")
    public ResponseEntity<SessionDto> sessionActive(@RequestBody UserDto userDto){
        String sessionid = userDto.getSessionid();
        return ResponseEntity.ok(this.sessionService.sessionActive(sessionid));
    }


    @GetMapping("/bookstore/registration/confirm")
    public String confirmRegistration(@RequestParam ("token") String token) throws ResourceNotFoundException {
        signInService.confirmRegistration(token);
        return "result";
    }

    @PostMapping("/bookstore/registration/resend")
    public ResponseEntity<Integer> resendConfirmLink(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        signInService.resendConfirmationLink(login);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/bookstore/user/accept")
    public ResponseEntity<String> userAccept(@RequestBody UserDto userDto){
        boolean accepted = this.signInService.userAccept(userDto);
        return ResponseEntity.ok(String.valueOf(accepted));
    }

    @GetMapping("/checkout/notice")
    public String checkouts(){
        return "checkoutnotice";
    }

}





