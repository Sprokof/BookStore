package online.book.store.service;

import online.book.store.dao.NoticeDao;
import online.book.store.entity.Book;
import online.book.store.entity.Notice;
import online.book.store.entity.User;
import online.book.store.entity.WaitList;
import online.book.store.enums.BookStatus;
import online.book.store.enums.NoticeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.sql.Date;
import java.time.Duration;
import java.util.ConcurrentModificationException;
import java.util.List;


@Component
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private UserService userService;

    @Autowired
    private WaitListService waitListService;

    @Autowired
    private BookService bookService;

    @Autowired
    private NoticeDao noticeDao;


    @Override
    public void createAvailableNotice(Book book) {
        new Thread(() -> {
            String title = book.getTitle();
            List<WaitList> waitLists = book.getWaitLists();
            int index = 0;
            int size = waitLists.size();

            while (index != size) {
                WaitList wl = book.getWaitLists().get(index);
                User user = wl.getUser();
                user.addNotice(NoticeMessage.NOW_AVAILABLE.setBookTitle(title));
                userService.updateUser(user);

                index++;
            }
            removeBook(book);
        }).start();
    }

    private void removeBook(Book book){
        int bookId = book.getId();
        this.waitListService.deleteBookFromWaitsLists(bookId);
        book.setAvailable(BookStatus.AVAILABLE);
        this.bookService.updateBook(book);
    }

    @Override
    public List<Notice> getAllNewNotice(User user) {
        int userId = user.getId();
        List<Notice> notices = this.noticeDao.getAllNewUsersNotices(userId);
        notices.sort(this::sortNotices);
        return notices;
    }

    private int sortNotices(Notice n1, Notice n2) {
        int result = 0;
        String stamp1 = n1.getStamp(), stamp2 = n2.getStamp();
        String date1 = stamp1.substring(0, stamp1.indexOf(","));
        String date2 = stamp2.substring(0, stamp1.indexOf(","));
        if(Date.valueOf(date1).after(Date.valueOf(date2))){
            result = 1;
        }
        else result = - 1;

        return result;
    }
}
