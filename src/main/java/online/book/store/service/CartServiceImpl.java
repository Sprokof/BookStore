package online.book.store.service;


import online.book.store.dao.CartDao;
import online.book.store.dto.CartDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;


    @Override
    public CartDto contains(Cart cart, Book book) {
        boolean contains = this.cartDao.contains(cart, book);
        return new CartDto(contains);

    }

    @Override
    public void addBookToCart(Book book, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setBook(book);
        cart.addItem(cartItem).updatePrices();
        updateCart(cart);

    }

    @Override
    public void removeBookFromCart(Book book, Cart cart){
        CartItem cartItem = getCartItemByBook(cart, book);
        cart.removeItem(cartItem).updatePrices();
        deleteCartItem(cartItem);
        updateCart(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        this.cartDao.updateCart(cart);
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

    @Override
    public CartItem getCartItemByBook(Cart cart, Book book) {
        return this.cartDao.getCartItemByBook(cart, book);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        this.cartDao.deleteCartItem(cartItem);
    }

    @Override
    public CartDto getItemsQuantity(Cart cart) {
        int cartId = cart.getId();
        int quantity = this.cartDao.getItemsQuantity(cartId);
        return new CartDto(quantity);
    }
}

