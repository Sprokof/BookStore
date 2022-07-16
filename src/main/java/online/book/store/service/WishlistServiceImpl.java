package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.User;
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
        user.getWishList().removeBook(book);
        userService.updateUserInSession(user);


    }

    @Override
    public void addBookToWishlist(Book book) {
        User user = userService.getCurrentUser();
        if(user.getWishList().getBooksId() == null) {
            user.getWishList().setBooksId(String.valueOf(book.getId()));
            }
            String booksId = user.getWishList().getBooksId();
            booksId += String.format(",%d,", book.getId());
            user.getWishList().setBooksId(sortBookList(booksId));
            userService.updateUserInSession(user);
        }


        private String sortBookList(String booksId){
            List<String> sorted = Arrays.stream(booksId.split("\\,")).
                    sorted(Comparator.comparingInt(Integer::parseInt)).
                    collect(Collectors.toList());
            String result = "";
            for(String id : sorted){
                result += id +", ";
            }
            return result;
        }
}
