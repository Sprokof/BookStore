package online.book.store.dao;


import online.book.store.dto.CartItemDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;

import java.math.BigInteger;

public interface CartDao {
    boolean contains(Cart cart, Book book);
    void updateCart(Cart cart);
    CartItem getCartItemById(int id);
    CartItem getCartItemByBook(Cart cart, Book book);
    void deleteCartItem(CartItem cartItem);
    Integer getItemsQuantity(int cartId);
    void updateCartItem(CartItem cartItem);
}
