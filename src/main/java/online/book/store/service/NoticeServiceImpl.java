package online.book.store.service;

import online.book.store.dao.NoticeDao;
import online.book.store.dto.NoticeDto;
import online.book.store.entity.Book;
import online.book.store.entity.Notice;
import online.book.store.entity.User;
import online.book.store.entity.WaitList;
import online.book.store.enums.BookStatus;
import online.book.store.enums.NoticeMessage;
import online.book.store.enums.NoticeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


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
                NoticeMessage.NOW_AVAILABLE.unsetBookTitle();

                index++;
            }
            removeBook(book);
        }).start();
    }

    private void removeBook(Book book) {
        int bookId = book.getId();
        this.waitListService.deleteBookFromWaitsLists(bookId);
        book.setAvailable(BookStatus.AVAILABLE);
        this.bookService.updateBook(book);
    }

    @Override
    public List<NoticeDto> getAllNewNotice(User user) {
        int userId = user.getId();
        List<Notice> notices = this.noticeDao.getFewUsersNotices(userId, 3);
        int count = this.noticeDao.getCountUsersNotices(userId);
        return notices.stream().map((n) -> new NoticeDto(n.getId(), n.getMessage(),
                n.getDate(), n.getStatus(), count)).collect(Collectors.toList());
    }


    @Override
    public NoticeDto getCountNewUsersNotices(User user) {
        int count = this.noticeDao.getCountNewUsersNotices(user.getId());
        return new NoticeDto(count);
    }

    @Override
    public void setNoticeStatus(int noticeId, NoticeStatus status) {
        Notice notice = getNoticeById(noticeId);
        notice.setStatus(status.getStatus());
        notice.setChangeDate(datestamp());
        this.noticeDao.updateNotice(notice);
    }

    @Override
    public Notice getNoticeById(int id) {
        return this.noticeDao.getNoticeById(id);
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while ((true)) {
                this.noticeDao.setAllReadNoticesToOld();
                this.noticeDao.deleteAllOldNotices();
            }
        }).start();
    }

    @Override
    public List<List<Notice>> allUserNotices(String login) {
        List<Notice> all = this.noticeDao.getAllUsersNotices(login);
        List<Notice> reads = this.noticeDao.getAllReadNotices(login);
        List<List<Notice>> notices = new ArrayList<>();
        notices.add(all);
        notices.add(reads);
        return notices;
    }

    @Override
    public NoticeDto noticesExists(String sessionid) {
        return new NoticeDto(this.noticeDao.noticesExists(sessionid));
    }

    private String datestamp() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(now);
    }
}
