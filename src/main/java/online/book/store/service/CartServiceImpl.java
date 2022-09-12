package online.book.store.service;


import online.book.store.dao.CartDao;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import online.book.store.session.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserService userService;


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

        updateCart(cart);


    }


    @Override
    public void updateCart(Cart cart) {
        User user = cart.getUser();
        userService.updateUserInSession(user);
    }


    @Override
    public void updateCartItem(CartItem cartItem, Cart cart) {
        cart.removeItem(cartItem);
        updateCart(cart);
    }

    @Override
    public void updateCartItem(CartItem cartItem, int quantity, Cart cart) {
        cartItem.setQuantity(quantity);
        cart.setCartItem(cartItem);
        updateCart(cart);

    }

    @Override
    public CartItem getCartItemById(int id) {
        return this.cartDao.getCartItemById(id);
    }
}

