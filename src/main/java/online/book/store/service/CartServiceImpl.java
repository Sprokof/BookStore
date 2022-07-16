package online.book.store.service;


import online.book.store.dao.CartDao;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;


    @Override
    public boolean bookAdded(Integer userId, Book book) {
        return this.cartDao.bookAdded(userId, book);
    }

    @Override
    public void addBookToCart(Book book) {
        User user = (User) httpSession.getAttribute("user");
        int quantity = 1;
        if(user.getCart() == null){
            user.setCart(new Cart());
        }
        if(bookAdded(user.getId(), book)){
            quantity += 1;
        }
        CartItem cartItem = new CartItem(book.getIsbn(), book.getPrice(), quantity);
        Cart cart = user.getCart();
        cart.addItem(cartItem).updatePrices();
        user.setCart(cart);

        userService.updateUserInSession(user);


    }


    @Override
    public void updateCart(Cart cart) {
        User user = userService.getCurrentUser();
        user.setCart(cart);
        userService.updateUserInSession(user);


    }

    @Override
    public void clearCart() {
        User user = userService.getCurrentUser();
        user.setCart(null);
        userService.updateUserInSession(user);
    }

    @Override
    public void updateCartItem(CartItem cartItem) {
     User currentUser =  userService.getCurrentUser();
     currentUser.getCart().removeItem(cartItem);
     updateCart(currentUser.getCart());
     userService.updateUserInSession(currentUser);

    }

    @Override
    public void updateCartItem(CartItem cartItem, int quantity) {
        User currentUser = userService.getCurrentUser();
        cartItem.setQuantity(quantity);
        currentUser.getCart().setCartItem(cartItem);

        userService.updateUserInSession(currentUser);

    }

    @Override
    public CartItem getCartItemById(int id) {
        return this.cartDao.getCartItemById(id);
    }
}

