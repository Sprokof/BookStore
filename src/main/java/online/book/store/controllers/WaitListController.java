package online.book.store.controllers;

import online.book.store.dto.WaitListDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller public class WaitListController {

    @PostMapping("/waitlist/add")
    public ResponseEntity<HttpStatus> addToWaitList(WaitListDto waitListDto){
        return null;
    }
}
