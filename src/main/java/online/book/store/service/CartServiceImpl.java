package online.book.store.service;


import online.book.store.dao.BookDao;
import online.book.store.dao.CartDao;
import online.book.store.dto.CartDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public CartDto contains(Cart cart, String isbn) {
        int bookId = bookDao.getBookIdByISBN(isbn);
        boolean contains = this.cartDao.contains(cart, bookId);
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
    public int removeBookFromCart(Book book, Cart cart){
        CartItem cartItem = getCartItemByBook(cart, book);
        cart.removeItem(cartItem).updatePrices();
        deleteCartItem(cartItem);
        updateCart(cart);
        return 200;
    }

    @Override
    public void updateCart(Cart cart) {
        this.cartDao.updateCart(cart);
    }


    @Override
    public int updateCartItem(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        cartItem.getCart().updatePrices();
        this.cartDao.updateCartItem(cartItem);
        return 200;
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

    @Override
    public void clearCart(Cart cart) {
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> tempList = new LinkedList<>();
        for(CartItem item : cartItems){
            tempList.add(item);
            this.cartDao.deleteCartItem(item);
        }
        for(CartItem item : tempList){
            cartItems.remove(item);
        }
        cart.updatePrices();
    updateCart(cart);
    }


}

