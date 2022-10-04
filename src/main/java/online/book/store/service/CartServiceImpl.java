package online.book.store.service;


import online.book.store.dao.CartDao;
import online.book.store.dto.CartItemDto;
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


    @Override
    public CartItemDto contains(Cart cart, Book book) {
        return new CartItemDto(this.cartDao.contains(cart, book));

    }

    @Override
    public void addBookToCart(Book book, Cart cart) {
        CartItem cartItem = new CartItem(book.getTitle(), book.getBookImageName(),
                book.getIsbn(), book.getPrice());
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

    @Override
    public CartItem getCartItemByBook(Cart cart, Book book) {
        return this.cartDao.getCartItemByBook(cart, book);
    }

    @Override
    public void deleteCartItem(CartItem cartItem) {
        this.cartDao.deleteCartItem(cartItem);
    }
}

