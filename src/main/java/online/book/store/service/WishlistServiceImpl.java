package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;


    @Override
    public void removeFromWishlist(Book book, Wishlist wishlist) {
        wishlist.remove(book);
        userService.updateUserInSession(wishlist.getUser());
    }

    @Override
    public void addBookToWishlist(Book book, Wishlist wishlist) {
        wishlist.addBook(book);
        userService.updateUserInSession(wishlist.getUser());
    }

    @Override
    public boolean contains(Book book, Wishlist wishlist) {
        return wishlist.getBooks().contains(book);
    }
}
