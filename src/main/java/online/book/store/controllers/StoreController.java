package online.book.store.controllers;

import online.book.store.dto.CategoryDto;
import online.book.store.dto.SessionDto;
import online.book.store.dto.UserDto;
import online.book.store.expections.ResourceNotFoundException;
import online.book.store.service.CategoryService;
import online.book.store.service.SessionService;
import online.book.store.service.SignService;
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
    private SignService signService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;


    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> popularCategories() {
        List<CategoryDto> categories = categoryService.getPopularCategories();
        return ResponseEntity.ok(categories);
    }


    @PostMapping("/session/validate")
    public ResponseEntity<SessionDto> validateSession(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(sessionService.getSessionData(userDto));
    }


    @DeleteMapping(value = "/invalidate")
    public ResponseEntity<Integer> invalidate(@RequestBody SessionDto sessionDto){
        String sessionid = sessionDto.getSessionid();
        this.sessionService.sessionInvalidate(sessionid);
        return ResponseEntity.ok(200);
    }


    @GetMapping("/registration/confirm")
    public String confirmRegistration(@RequestParam ("token") String token) throws ResourceNotFoundException {
        signService.confirmRegistration(token);
        return "result";
    }

    @PostMapping("/registration/resend")
    public ResponseEntity<Integer> resendConfirmLink(@RequestBody UserDto userDto) {
        String login = userDto.getLogin();
        signService.resendConfirmationLink(login);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/bookstore/accept/user")
    public ResponseEntity<String> userAccept(@RequestParam("user") String login){
        boolean accepted = this.userService.userAccepted(login);
        return ResponseEntity.ok(String.valueOf(accepted));
    }

    @GetMapping("/error")
    public Exception authorize() {
        throw new ResourceNotFoundException();
    }


    @GetMapping("/validate/user/request")
    public ResponseEntity<UserDto> validateRequest(@RequestHeader("session") String sessionid){
        UserDto userDto = this.signService.validateRequest(sessionid);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/about")
    public String about () {
        return "about";
    }

}





