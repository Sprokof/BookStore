package online.book.store.controllers;

import online.book.store.dto.NoticeDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.Notice;
import online.book.store.entity.User;
import online.book.store.enums.NoticeStatus;
import online.book.store.service.NoticeService;
import online.book.store.service.SessionService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService service;

    @Autowired
    private SessionService sessionService;

    @GetMapping(value = "/notice/get")
    public ResponseEntity<List<NoticeDto>> newNotice(@RequestHeader("session") String sessionid) {
        User user = this.sessionService.getCurrentUser(sessionid, false);
        return ResponseEntity.ok(this.noticeService.getAllNewNotice(user));

    }

    @GetMapping(value = "/notice/count/get")
    public ResponseEntity<NoticeDto> countNotices(@RequestHeader("session") String sessionid) {
        User user = this.sessionService.getCurrentUser(sessionid, false);
        return ResponseEntity.ok(this.noticeService.getCountNewUsersNotices(user));
    }

    @PostMapping("/notice/status/set")
    public ResponseEntity<HttpStatus> markAsRead(@RequestBody NoticeDto dto){
        int id = dto.getId();
        this.noticeService.setNoticeStatus(id, NoticeStatus.READ);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @GetMapping("/notices/all")
    public String allNotices(@RequestParam("user") String login, Model model) {
        List<List<Notice>> notices = this.noticeService.allUserNotices(login);
        int size = notices.get(0).size();
        if(size == 0) return "result";
        model.addAttribute("all", notices.get(0));
        model.addAttribute("read", notices.get(1));
        return "notifications";
    }

    @GetMapping("/notice/exist")
    public ResponseEntity<NoticeDto> noticeExist(@RequestHeader("session") String sessionid){
           NoticeDto dto = this.noticeService.noticesExists(sessionid);
           return ResponseEntity.ok(dto);
    }

}
