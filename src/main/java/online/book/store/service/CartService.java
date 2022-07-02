package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.User;

public interface CartService {
    void addBookToCart(Book book);
    boolean bookAdded(Integer userId, Book book);
    void updateCart(Cart cart);
    void clearCart();
}
