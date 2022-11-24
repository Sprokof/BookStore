package online.book.store.controllers;


import online.book.store.dto.CartDto;
import online.book.store.dto.CheckoutDto;
import online.book.store.entity.Cart;
import online.book.store.entity.User;
import online.book.store.service.CartService;
import online.book.store.service.CheckoutService;
import online.book.store.service.SessionService;
import online.book.store.validation.CheckoutValidation;
import online.book.store.validation.ValidateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CheckoutController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CheckoutValidation checkoutValidation;

    @Autowired
    private CheckoutService checkoutService;


    @DeleteMapping("/cart/clear")
    public ResponseEntity<Integer> clearCart(@RequestBody CartDto cartDto){
        String sessionid = cartDto.getSessionid();
        User user = this.sessionService.getCurrentUser(sessionid);
        Cart cart = user.getCart();
        cartService.clearCart(cart);
        return ResponseEntity.ok(200);
    }

    @PostMapping("/validate/checkout")
    public ResponseEntity<Map<String, String>> validateCheckout(@RequestBody CheckoutDto checkoutDto){
        checkoutValidation.validation(checkoutDto);
        if(!checkoutValidation.hasErrors()){
            User user = this.sessionService.getCurrentUser(checkoutDto.getSessionid());
            checkoutService.saveCheckoutInfo(checkoutDto, user);
        }
        return ResponseEntity.ok(checkoutValidation.validationErrors());
    }

    @GetMapping("/checkout/saved")
    public ResponseEntity<String> checkoutSaved(@RequestParam("sessionid") String sessionid){
        User user = this.sessionService.getCurrentUser(sessionid);
        return ResponseEntity.ok(String.valueOf(this.checkoutService.checkoutSaved(user)));
    }

    @GetMapping("/checkout/data")
    public ResponseEntity<CheckoutDto> getCheckoutsData(@RequestParam("sessionid") String sessionid){
        User user = this.sessionService.getCurrentUser(sessionid);
        return ResponseEntity.ok(this.checkoutService.getCheckoutsData(user));
    }
}
