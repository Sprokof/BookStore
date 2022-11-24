package online.book.store.controllers;

import online.book.store.dto.*;
import online.book.store.entity.*;
import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
public class CartController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CartService cartService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;


    @GetMapping("/cart")
    public String cart(@RequestParam("user") String login, Model model){
        Cart cart = userService.getUserByLogin(login).getCart();
        if(cart.isEmpty()){
            return "result";
        }
        model.addAttribute("cart", cart);
        return "cart";
    }


    @DeleteMapping("/cart/item/remove")
    public ResponseEntity<Integer> removeBook(@RequestBody CartDto cartDto){
        String isbn = cartDto.getIsbn();
        String sessionid = cartDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        Book book = bookService.getBookByIsbn(isbn);
        cartService.removeBookFromCart(book, user.getCart());
        return ResponseEntity.ok(200);

    }

    @PostMapping("/cart/item/add")
    public ResponseEntity<Integer> addBook(@RequestBody CartDto cartDto){
        String isbn = cartDto.getIsbn();
        String sessionid = cartDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        Book book = bookService.getBookByIsbn(isbn);
        cartService.addBookToCart(book, user.getCart());
        return ResponseEntity.ok(200);
    }

    @GetMapping("/cart/item/contains")
    public ResponseEntity<CartDto> contains(@RequestHeader("session") String sessionid, @RequestParam String isbn){
        User user = sessionService.getCurrentUser(sessionid);
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(cartService.contains(user.getCart(), book));
    }

    @GetMapping("/cart/item/quantity")
    public ResponseEntity<CartDto> itemsQuantity(@RequestHeader("session") String sessionid){
        if(!sessionService.sessionExist(sessionid)) return ResponseEntity.ok(new CartDto(0));
        Cart cart = this.sessionService.getCurrentUser(sessionid).getCart();
        return ResponseEntity.ok(cartService.getItemsQuantity(cart));
    }

    @PutMapping("/cart/item/set")
    public ResponseEntity<Integer> setItem(@RequestBody CartItemDto cartItemDto){
        String sessionid = cartItemDto.getSessionid();
        String isbn = cartItemDto.getIsbn();
        int quantity = Integer.parseInt(cartItemDto.getQuantity());
        User user = sessionService.getCurrentUser(sessionid);
        Cart cart = user.getCart();
        Book book = bookService.getBookByIsbn(isbn);
        CartItem cartItem = cartService.getCartItemByBook(cart, book);
        cartService.updateCartItem(cartItem, quantity);
        return ResponseEntity.ok(200);
    }

}
