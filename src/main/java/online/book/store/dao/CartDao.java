package online.book.store.dao;


import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;

public interface CartDao {
    boolean bookAdded(Integer userId, Book book);
    void updateCart(Cart cart);
    CartItem getCartItemById(int id);
}
