package online.book.store.service;

import online.book.store.dao.WaitListDao;
import online.book.store.dto.WaitListDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.WaitList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WaitListServiceImpl implements WaitListService {

    @Autowired
    private WaitListDao waitListDao;

    @Autowired
    private UserService userService;


    @Override
    public WaitListDto contains(int bookId, WaitList waitList) {
        return new WaitListDto(this.waitListDao.contains(bookId, waitList));

    }
    @Override
    public void updateWaitList(WaitList waitList) {
        this.waitListDao.updateWaitList(waitList);
    }

    @Override
    public void addToWaitList(Book book, WaitList waitList) {
        waitList.addBook(book);
        this.updateWaitList(waitList);
    }

    @Override
    public void deleteBookFromWaitsLists(int bookId) {
        this.waitListDao.deleteFromWaitsLists(bookId);
    }
}
