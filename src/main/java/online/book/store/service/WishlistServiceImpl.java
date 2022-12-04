package online.book.store.service;

import online.book.store.dao.BookDao;
import online.book.store.dao.WishlistDao;
import online.book.store.dto.WishlistDto;
import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private BookDao bookDao;


    @Override
    public int removeFromWishlist(Book book, Wishlist wishlist) {
        wishlist.remove(book);
        updateWishlist(wishlist);
        return 200;
    }

    @Override
    public void addBookToWishlist(Book book, Wishlist wishlist) {
        wishlist.addBook(book);
        updateWishlist(wishlist);
    }

    @Override
    public WishlistDto contains(String isbn, Wishlist wishlist) {
        int bookId = this.bookDao.getBookIdByISBN(isbn);
        boolean contains = this.wishlistDao.contains(bookId, wishlist);
        return new WishlistDto(contains);
    }

    @Override
    public void updateWishlist(Wishlist wishlist) {
        this.wishlistDao.updateWishlist(wishlist);
    }
}
