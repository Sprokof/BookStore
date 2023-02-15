package online.book.store.service;


import online.book.store.dto.NoticeDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import java.util.List;

public interface NoticeService {
    void createAvailableNotice(Book book);
    List<NoticeDto> getAllNewNotice(User user);
    NoticeDto getCountNewUsersNotices(User user);
}
