package online.book.store.dao;


import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;

public interface WishlistDao {
    boolean contains(Book book, Wishlist wishlist);
    void updateWishlist(Wishlist wishlist);
}
