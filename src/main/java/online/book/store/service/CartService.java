package online.book.store.service;

import online.book.store.dto.CartDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.UserSession;
import org.springframework.http.HttpStatus;

public interface CartService {
    void addBookToCart(Book book, Cart cart);
    int removeBookFromCart(Book book, Cart cart);
    CartDto contains(Cart cart, Book book);
    void updateCart(Cart cart);
    int updateCartItem(CartItem cartItem, int quantity);
    CartItem getCartItemByBook(Cart cart, Book book);
    void deleteCartItem(CartItem cartItem);
    CartDto getItemsQuantity(Cart cart);
    void clearCart(Cart cart);


}
