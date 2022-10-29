package online.book.store.controllers;


import online.book.store.dto.CartDto;
import online.book.store.entity.Cart;
import online.book.store.entity.User;
import online.book.store.service.CartService;
import online.book.store.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class CheckoutController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CartService cartService;

    @PostMapping("/home/cart/clear")
    private ResponseEntity<Integer> clearCart(@RequestBody CartDto cartDto){
        String sessionid = cartDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid);
        Cart cart = user.getCart();
        cartService.clearCart(cart);
        return ResponseEntity.ok(200);
    }

}
