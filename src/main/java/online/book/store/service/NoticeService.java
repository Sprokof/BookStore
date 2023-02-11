package online.book.store.service;


import online.book.store.entity.Book;
import online.book.store.entity.Notice;
import online.book.store.entity.User;
import reactor.core.publisher.Flux;

import java.util.List;

public interface NoticeService {
    void createAvailableNotice(Book book);
    List<Notice> getAllNewNotice(User user);
}
