package online.book.store.service;

import online.book.store.dao.SessionStatisticsDao;
import online.book.store.entity.SessionStatistics;
import online.book.store.entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class SessionStatisticsService {

    @Autowired
    private SessionStatisticsDao statisticsDao;

    public void incrementActiveSession(boolean sessionFirst){
        SessionStatistics statistics = statistics();
        long currentCount = statistics.getActiveSessionCount();
        currentCount += 1;
        statistics.setActiveSessionCount(currentCount);
        if(statisticsDao.updateStatistics(statistics)) {
            if(sessionFirst) incrementUsersCount(statistics);
        }
    }

    public void decrementActiveSession(boolean sessionFirst) {
        SessionStatistics statistics = statistics();
        long currentCount = statistics.getActiveSessionCount();
        currentCount -= 1;
        statistics.setActiveSessionCount(currentCount);
        if(statisticsDao.updateStatistics(statistics)){
            if(sessionFirst) decrementUsersCount(statistics);
        }
    }

    private void incrementUsersCount(SessionStatistics statistics){
            long count = statistics.getUsersCount();
            statistics.setUsersCount(count + 1);
            statisticsDao.updateStatistics(statistics);
    }

    private void decrementUsersCount(SessionStatistics statistics){
        long count = statistics.getUsersCount();
        statistics.setUsersCount(count - 1);
        statisticsDao.updateStatistics(statistics);
    }

    public SessionStatistics statistics(){
        return this.statisticsDao.getStatistics();
    }
}


