package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.WaitList;
import online.book.store.enums.BookStatus;
import online.book.store.enums.NoticeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


    @Override
    public void createAvailableNotice(Book book) {
        String title = book.getTitle();
        List<WaitList> waitLists = book.getWaitLists();
        int index = 0;
        int size = waitLists.size();

        while(index != size) {
            WaitList wl = book.getWaitLists().get(index);
            User user = wl.getUser();
            user.addNotice(NoticeMessage.NOW_AVAILABLE.setBookTitle(title));
            userService.updateUser(user);

            index++;
        }
        removeBook(book);
    }

    private void removeBook(Book book){
        int bookId = book.getId();
        this.waitListService.deleteBookFromWaitsLists(bookId);
        book.setAvailable(BookStatus.AVAILABLE);
        this.bookService.updateBook(book);
    }

}
