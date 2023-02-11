package online.book.store.dao;

import online.book.store.entity.Notice;

import java.util.List;

public interface NoticeDao {
    List<Notice> getAllNewUsersNotices(int userId);
}
