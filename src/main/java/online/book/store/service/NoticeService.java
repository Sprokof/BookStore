package online.book.store.service;


import online.book.store.dto.NoticeDto;
import online.book.store.entity.Book;
import online.book.store.entity.Notice;
import online.book.store.entity.User;
import online.book.store.enums.NoticeStatus;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

public interface NoticeService extends CommandLineRunner {
    void createAvailableNotice(Book book);
    List<NoticeDto> getAllNewNotice(User user);
    NoticeDto getCountNewUsersNotices(User user);
    void setNoticeStatus(int noticeId, NoticeStatus status);
    Notice getNoticeById(int id);
}
