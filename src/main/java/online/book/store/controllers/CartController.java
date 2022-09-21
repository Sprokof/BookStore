package online.book.store.controllers;

import online.book.store.dto.CartItemDto;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class CartController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private SignInService signInService;


    @GetMapping("/home/cart")
    public String cart(Model model, HttpServletRequest request){
        Cart cart = signInService.getUserFromRequest(request).getCart();
        model.addAttribute("cart", cart);
        return "cart";
    }


    @PostMapping("/home/cart/set")
    public ResponseEntity.BodyBuilder addCartItem(@RequestBody CartItemDto dto){
        User user = signInService.getSavedUser();
        int id = (Integer.parseInt(dto.getCartItemId()));
        int quantity = (Integer.parseInt(dto.getQuantity()));

        CartItem itemToSet = cartService.getCartItemById(id);

        cartService.updateCartItem(itemToSet, quantity, user.getCart());

        return ResponseEntity.status(200);

    }

    @PostMapping("/home/cart/remove/")
    public ResponseEntity.BodyBuilder removeCartItem(@RequestBody String itemId){
        User user = signInService.getSavedUser();
        int cartItemId = (Integer.parseInt(itemId));

        CartItem cartItem = cartService.getCartItemById(cartItemId);

        cartService.updateCartItem(cartItem, user.getCart());

        return ResponseEntity.status(200);

    }

    @PostMapping("/home/cart/add")
    public ResponseEntity<?> addBookToCart(@RequestParam("title") String title){
        User user = signInService.getSavedUser();
        Book book = bookService.getBookByTitle(title);
        cartService.addBookToCart(book, user.getCart());
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
