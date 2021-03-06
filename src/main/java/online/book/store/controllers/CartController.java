package online.book.store.controllers;

import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import online.book.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private HttpSession httpSession;



    @ModelAttribute("cart")
    public Cart getUserCart(){
        return ((User) httpSession.
                getAttribute("user")).getCart();
    }

    @GetMapping("/home/cart")
    public String cart(){
        return "cart";
    }


    @PostMapping("/home/cart/add/item")
    public String addCartItem(@RequestParam("item") String itemId, String quantity, Model model) throws NullPointerException{
        model.addAttribute("quantity", quantity);

        int cartItemId = (Integer.parseInt(itemId));

        Cart userCart = ((Cart) (model.getAttribute("cart")));

        assert userCart != null;
        CartItem cartItem = cartService.getCartItemById(cartItemId);

        cartService.updateCartItem(cartItem, Integer.parseInt(quantity));
        return "cart";

    }

    @PostMapping("/home/cart/remove/item")
    public String removeCartItem(@RequestParam("item") String itemId, Model model){

        int cartItemId = (Integer.parseInt(itemId));

        Cart cart = (Cart) model.getAttribute("cart");

        assert cart != null;

        CartItem cartItem = cartService.getCartItemById(cartItemId);

        cartService.updateCartItem(cartItem);

        return "cart";

    }
}
