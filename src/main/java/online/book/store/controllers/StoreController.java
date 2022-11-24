package online.book.store.controllers;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.CategoryService;
import online.book.store.service.SessionService;
import online.book.store.service.SignService;
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
    private SignService signService;

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
        this.signService.autologin(userDto);
        return ResponseEntity.ok(200);
    }


    @PostMapping("/session/active")
    public ResponseEntity<SessionDto> sessionActive(@RequestBody UserDto userDto){
        String sessionid = userDto.getSessionid();
        return ResponseEntity.ok(this.sessionService.sessionActive(sessionid));
    }


    @GetMapping("/bookstore/registration/confirm")
    public String confirmRegistration(@RequestParam ("token") String token) throws ResourceNotFoundException {
        signService.confirmRegistration(token);
        return "result";
    }

    @PostMapping("/bookstore/registration/resend")
    public ResponseEntity<Integer> resendConfirmLink(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        signService.resendConfirmationLink(login);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/bookstore/accept/user")
    public ResponseEntity<String> userAccept(@RequestParam("user") String login){
        boolean accepted = this.signService.userAccept(login);
        return ResponseEntity.ok(String.valueOf(accepted));
    }

    @GetMapping("/error")
    public Exception authorizeData() {
        throw new ResourceNotFoundException();
    }


    @GetMapping("/validate/user/request")
    public ResponseEntity<UserDto> validateRequest(@RequestParam("sessionid") String sessionid){
        UserDto userDto = this.signService.validateRequest(sessionid);
        return ResponseEntity.ok(userDto);
    }

}





