package online.book.store.service;

import online.book.store.dto.WaitListDto;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.WaitList;

public interface WaitListService {
    WaitListDto contains(int bookId, WaitList waitList);
    void updateWaitList(WaitList waitList);
    void addToWaitList(Book book, WaitList waitList);

}
