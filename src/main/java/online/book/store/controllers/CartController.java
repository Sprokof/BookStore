package online.book.store.controllers;

import online.book.store.dto.CartItemDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import online.book.store.service.CartService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @ModelAttribute("cart")
    public Cart getUserCart(){
        return userService.getCurrentUser().getCart();
    }

    @GetMapping("/home/cart")
    public String cart(){
        return "cart";
    }


    @PostMapping("/home/cart/set")
    public ResponseEntity.BodyBuilder addCartItem(@RequestBody CartItemDto dto){
        int id = (Integer.parseInt(dto.getCartItemId()));
        int quantity = (Integer.parseInt(dto.getQuantity()));

        CartItem itemToSet = cartService.getCartItemById(id);

        cartService.updateCartItem(itemToSet, quantity);

        return ResponseEntity.status(200);

    }

    @PostMapping("/home/cart/remove/")
    public ResponseEntity.BodyBuilder removeCartItem(@RequestBody String itemId){

        int cartItemId = (Integer.parseInt(itemId));

        CartItem cartItem = cartService.getCartItemById(cartItemId);

        cartService.updateCartItem(cartItem);

        return ResponseEntity.status(200);

    }
}
