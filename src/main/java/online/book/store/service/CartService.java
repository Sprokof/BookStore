package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;

public interface CartService {
    void addBookToCart(Book book, Cart cart);
    boolean bookAdded(Integer userId, Book book);
    void updateCart(Cart cart);
    void clearCart(Cart cart);
    void updateCartItem(CartItem cartItem, Cart cart);
    void updateCartItem(CartItem cartItem, int quantity, Cart cart);
    CartItem getCartItemById(int id);
}
