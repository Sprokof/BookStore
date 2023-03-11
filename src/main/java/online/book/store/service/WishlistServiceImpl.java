package online.book.store.service;

import cache.LFUCache;
import cache.LFUCacheSingleton;
import online.book.store.dao.BookDao;
import online.book.store.dao.WishlistDao;
import online.book.store.dto.WishlistDto;
import online.book.store.engines.Page;
import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
@Component
public class WishlistServiceImpl implements WishlistService{

    private final LFUCache cache = LFUCacheSingleton.cacheInstance();
    @Autowired
    private WishlistDao wishlistDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public HttpStatus removeFromWishlist(Book book, Wishlist wishlist) {
        wishlist.remove(book);
        cache.updateIfExist(getUserData(wishlist), wishlist);
        updateWishlist(wishlist);
        return HttpStatus.OK;
    }

    @Override
    public void addBookToWishlist(Book book, Wishlist wishlist) {
        wishlist.addBook(book);
        cache.updateIfExist(getUserData(wishlist), wishlist);
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

    @Override
    public List<Page.Row> map(Wishlist wishlist) {
        int count = Page.Row.MAX_COUNT_BOOKS_IN_ROWS;
        List<Page.Row> rows = new LinkedList<>();
        List<Book> books = wishlist.getBooks();
        for (int i = 0; i < books.size(); i += count) {
            rows.add(new Page.Row().setBooksInRow(books.subList(i,
                    Math.min(i + count, books.size()))));
        }
        return rows;
    }

    private String[] getUserData(Wishlist wishlist){
        return new String[] {wishlist.getUser().getEmail(),
                wishlist.getUser().getUsername()};
    }

}
