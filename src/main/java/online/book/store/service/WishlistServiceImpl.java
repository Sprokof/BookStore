package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.User;
import online.book.store.entity.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;


    @Override
    public void addToCart(Book book) {
        cartService.addBookToCart(book);

    }

    @Override
    public void removeFromWishlist(Book book) {
        User user = userService.getCurrentUser();
        user.getWishList().remove(book);
        userService.updateUserInSession(user);
    }

    @Override
    public void addBookToWishlist(Book book) {
        User user = userService.getCurrentUser();
        user.getWishList().addBook(book);
        userService.updateUserInSession(user);

        }
}
