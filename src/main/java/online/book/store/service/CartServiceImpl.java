package online.book.store.service;


import lombok.Setter;
import online.book.store.dao.CartDao;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import online.book.store.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserService userService;

    @Autowired
    private SignInService signInService;


    @Override
    public boolean bookAdded(Integer userId, Book book) {
        return this.cartDao.bookAdded(userId, book);
    }

    @Override
    public void addBookToCart(Book book, Cart cart) {
        int quantity = 1;

        if(bookAdded(cart.getUser().getId(), book)){
            quantity += 1;
        }
        CartItem cartItem = new CartItem(book.getIsbn(), book.getPrice(), quantity);
        cart.addItem(cartItem).updatePrices();

        userService.updateUserInSession(cart.getUser());


    }


    @Override
    public void updateCart(Cart cart) {
        User user = cart.getUser();;
        user.setCart(cart);
        userService.updateUserInSession(user);


    }

    @Override
    public void clearCart(Cart cart) {
        User user = cart.getUser();;
        user.setCart(null);
        userService.updateUserInSession(user);
    }

    @Override
    public void updateCartItem(CartItem cartItem, Cart cart) {
     cart.removeItem(cartItem);
     updateCart(cart);
     userService.updateUserInSession(cart.getUser());

    }

    @Override
    public void updateCartItem(CartItem cartItem, int quantity, Cart cart) {
        cartItem.setQuantity(quantity);
        cart.setCartItem(cartItem);

        userService.updateUserInSession(cart.getUser());

    }

    @Override
    public CartItem getCartItemById(int id) {
        return this.cartDao.getCartItemById(id);
    }
}

