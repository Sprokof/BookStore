package online.book.store.service;

import online.book.store.dao.WishlistDao;
import online.book.store.dto.WishlistDto;
import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    private WishlistDao wishlistDao;


    @Override
    public void removeFromWishlist(Book book, Wishlist wishlist) {
        wishlist.remove(book);
        updateWishlist(wishlist);
    }

    @Override
    public void addBookToWishlist(Book book, Wishlist wishlist) {
        wishlist.addBook(book);
        updateWishlist(wishlist);
    }

    @Override
    public WishlistDto contains(Book book, Wishlist wishlist) {
        boolean contains = this.wishlistDao.contains(book, wishlist);
        return new WishlistDto(contains);
    }

    @Override
    public void updateWishlist(Wishlist wishlist) {
        this.wishlistDao.updateWishlist(wishlist);
    }
}
