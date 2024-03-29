package online.book.store.service;

import online.book.store.dto.WishlistDto;
import online.book.store.engines.Page;
import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface WishlistService {
    void addBookToWishlist(Book book, Wishlist wishlist);
    HttpStatus removeFromWishlist(Book book, Wishlist wishlist);
    WishlistDto contains(String isbn, Wishlist wishlist);
    void updateWishlist(Wishlist wishlist);
    List<Page.Row> map(Wishlist wishlist);
}
