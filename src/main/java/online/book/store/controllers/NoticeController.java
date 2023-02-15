package online.book.store.controllers;

import online.book.store.dto.NoticeDto;
import online.book.store.dto.UserDto;
import online.book.store.entity.Notice;
import online.book.store.entity.User;
import online.book.store.service.NoticeService;
import online.book.store.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private SessionService sessionService;

    @GetMapping(value = "/notice/get")
    public ResponseEntity<List<NoticeDto>> newNotice(@RequestHeader("session") String sessionid) {
        User user = this.sessionService.getCurrentUser(sessionid);
        return ResponseEntity.ok(this.noticeService.getAllNewNotice(user));

    }

    @GetMapping(value = "/notice/count/get")
    public ResponseEntity<NoticeDto> countNotices(@RequestHeader("session") String sessionid) {
        User user = this.sessionService.getCurrentUser(sessionid);
        return ResponseEntity.ok(this.noticeService.getCountNewUsersNotices(user));
    }


}
