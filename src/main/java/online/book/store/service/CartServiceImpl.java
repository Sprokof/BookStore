package online.book.store.service;


import online.book.store.dao.CartDao;
import online.book.store.dto.ResponseDto;
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
    public ResponseDto contains(Cart cart, Book book) {
        ResponseDto responseDto = new ResponseDto();
        boolean contains = this.cartDao.contains(cart, book);
        responseDto.setItemContains(contains);
        return responseDto;

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

    @Override
    public ResponseDto getItemsQuantity(Cart cart) {
        ResponseDto responseDto = new ResponseDto();
        int cartId = cart.getId();
        int quantity = this.cartDao.getItemsQuantity(cartId);
        responseDto.setQuantity(quantity);
        return responseDto;
    }
}

