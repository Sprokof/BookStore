package online.book.store.dao;

import online.book.store.entity.WaitList;

public interface WaitListDao {
    boolean contains(int bookId, WaitList waitList);
    void updateWaitList(WaitList waitList);

}
