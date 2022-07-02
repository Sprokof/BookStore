package online.book.store.service;


import online.book.store.dao.CartDao;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

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
        cart.addItem(cartItem);
        cart.setTotal(calculateTotalPrice(cart));
        cart.setSubtotal(calculateSubTotalPrice(cart));
        cart.setCount(calculateCountCartItems(cart));
        user.setCart(cart);

        httpSession.setAttribute("user", user);
        userService.updateUser(user);


    }



    private double calculateTotalPrice(Cart cart){
        double shippingPrice = 17d;
        double totalSum = 0;
        for(CartItem item : cart.getCartItems()){
            totalSum += item.getTotal();
        }
        return totalSum + shippingPrice;
    }

    private double calculateSubTotalPrice(Cart cart){
        double subTotalSum = 0;
        for(CartItem item : cart.getCartItems()){
            subTotalSum += item.getTotal();
        }
        return subTotalSum;
    }

    private int calculateCountCartItems(Cart cart){

        return cart.getCartItems().size();
    }

    @Override
    public void updateCart(Cart cart) {
        cart.setTotal(calculateTotalPrice(cart));
        this.cartDao.updateCart(cart);
    }

    @Override
    public void clearCart() {
        User user = (User) httpSession.getAttribute("user");
        user.setCart(null);
        httpSession.setAttribute("user", user);
        userService.updateUser(user);
    }
}

