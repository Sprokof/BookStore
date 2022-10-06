package online.book.store.service;

import online.book.store.dto.ResponseDto;
import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;

public interface WishlistService {
    void addBookToWishlist(Book book, Wishlist wishlist);
    void removeFromWishlist(Book book, Wishlist wishlist);
    ResponseDto contains(Book book, Wishlist wishlist);
    void updateWishlist(Wishlist wishlist);
}
