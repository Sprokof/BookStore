package online.book.store.service;

import online.book.store.entity.Book;

public interface WishlistService {
    void addToCart(Book book);
    void addBookToWishlist(Book book);
    void removeFromWishlist(Book book);
}
