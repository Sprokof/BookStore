package online.book.store.controllers;

import online.book.store.dto.*;
import online.book.store.entity.*;
import online.book.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


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


    @GetMapping("/home/cart")
    public String cart(@RequestParam("user") String login, Model model){
        Cart cart = userService.getUserByLogin(login).getCart();
        model.addAttribute("cart", cart);
        return "cart";
    }


    @PostMapping("/home/cart/remove")
    public ResponseEntity<Integer> removeBook(@RequestBody CartDto cartDto){
        String isbn = cartDto.getIsbn();
        String sessionid = cartDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        Book book = bookService.getBookByIsbn(isbn);
        cartService.removeBookFromCart(book, user.getCart());
        return ResponseEntity.ok(200);

    }

    @PostMapping("/home/cart/add")
    public ResponseEntity<Integer> addBook(@RequestBody CartDto cartDto){
        String isbn = cartDto.getIsbn();
        String sessionid = cartDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        Book book = bookService.getBookByIsbn(isbn);
        cartService.addBookToCart(book, user.getCart());
        return ResponseEntity.ok(200);
    }

    @PostMapping("/home/cart/contains")
    public ResponseEntity<CartDto> contains(@RequestBody CartDto cartDto){
        String isbn = cartDto.getIsbn();
        String sessionid = cartDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(cartService.contains(user.getCart(), book));
    }

    @PostMapping("/home/cart/quantity")
    public ResponseEntity<CartDto> itemsQuantity(@RequestBody UserDto userDto){
        String sessionid = userDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        Cart cart = user.getCart();
        return ResponseEntity.ok(cartService.getItemsQuantity(cart));
    }

    @PostMapping("/home/cart/item/set")
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
