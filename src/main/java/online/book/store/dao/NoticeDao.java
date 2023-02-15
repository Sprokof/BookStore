package online.book.store.dao;

import online.book.store.entity.Notice;

import java.util.List;

public interface NoticeDao {
    List<Notice> getAllUsersNotices(int userId);
    int getCountNewUsersNotices(int userId);
}
