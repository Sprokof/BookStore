package online.book.store.dao;


import online.book.store.entity.Book;
import online.book.store.entity.Cart;

public interface CartDao {
    boolean bookAdded(Integer userId, Book book);
    void updateCart(Cart cart);
}
