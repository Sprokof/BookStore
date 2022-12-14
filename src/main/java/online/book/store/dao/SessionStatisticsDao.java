package online.book.store.dao;

import online.book.store.entity.SessionStatistics;

public interface SessionStatisticsDao {
    SessionStatistics getStatistics();
    boolean updateStatistics(SessionStatistics statistics);
}
