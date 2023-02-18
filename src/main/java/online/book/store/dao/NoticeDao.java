package online.book.store.dao;

import online.book.store.entity.Notice;

import java.util.List;

public interface NoticeDao {
    List<Notice> getFewUsersNotices(int userId, int count);
    void setAllReadNoticesToOld();
    int getCountNewUsersNotices(int userId);
    void updateNotice(Notice notice);
    Notice getNoticeById(int id);

    int getCountUsersNotices(int userId);
}
