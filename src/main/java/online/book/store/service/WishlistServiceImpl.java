package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;


@Component
public class WishlistServiceImpl implements WishlistService{

    @Autowired
    CartService cartService;

    @Autowired
    HttpSession httpSession;

    @Autowired
    UserService userService;


    @Override
    public void addToCart(Book book) {
        cartService.addBookToCart(book);

    }

    @Override
    public void removeFromWishlist(Book book) {
        User user = (User) httpSession.getAttribute("user");
        user.getWishList().removeBook(book);
        userService.updateUser(user);
        httpSession.setAttribute("user", user);

    }

    @Override
    public void addBookToWishlist(Book book) {
        User user = (User) httpSession.getAttribute("user");
        if(user.getWishList().getBooksId() == null) {
            user.getWishList().setBooksId(String.valueOf(book.getId()));
            }
            String booksId = user.getWishList().getBooksId();
            booksId += String.format(",%d,", book.getId());
            user.getWishList().setBooksId(booksId);
            userService.updateUser(user);
            httpSession.setAttribute("user", user);
        }
}
