package online.book.store.controllers;

import online.book.store.dto.*;
import online.book.store.entity.Book;
import online.book.store.entity.Cart;
import online.book.store.entity.CartItem;
import online.book.store.entity.User;
import online.book.store.service.*;
import online.book.store.session.SessionStorage;
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
    private SessionStorage sessionStorage;


    @GetMapping("/home/cart")
    public String cart(Model model, HttpServletRequest request){
        Cart cart = sessionStorage.getUser(request).getCart();
        model.addAttribute("cart", cart);
        return "cart";
    }


    @PostMapping("/home/cart/set")
    public ResponseEntity.BodyBuilder addCartItem(@RequestBody CartItemDto dto, HttpServletRequest request){
        User user = sessionStorage.getUser(request);
        int id = (Integer.parseInt(dto.getCartItemId()));
        int quantity = (Integer.parseInt(dto.getQuantity()));

        CartItem itemToSet = cartService.getCartItemById(id);

        cartService.updateCartItem(itemToSet, quantity, user.getCart());

        return ResponseEntity.status(200);

    }

    @PostMapping("/home/cart/remove")
    public ResponseEntity<Integer> removeCartItem(@RequestBody String isbn, HttpServletRequest request){
        User user = sessionStorage.getUser(request);
        Book book = bookService.getBookByIsbn(isbn);
        cartService.removeBookFromCart(book, user.getCart());
        return ResponseEntity.ok(200);

    }

    @PostMapping("/home/cart/add")
    public ResponseEntity<Integer> addBookToCart(@RequestBody String isbn, HttpServletRequest request){
        User user = sessionStorage.getUser(request);
        Book book = bookService.getBookByIsbn(isbn);
        cartService.addBookToCart(book, user.getCart());
        return ResponseEntity.ok(200);
    }

    @PostMapping("/home/cart/contains")
    public ResponseEntity<ResponseDto> contains(@RequestBody RequestDto requestDto){
        User user = sessionStorage.getUser(requestDto.getUserLogin());
        String isbn = requestDto.getIsbn();
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(cartService.contains(user.getCart(), book));
    }

    @GetMapping("/home/cart/quantity")
    public ResponseEntity<ResponseDto> cartQuantity(HttpServletRequest request){
        Cart cart = sessionStorage.getUser(request).getCart();
        return ResponseEntity.ok(cartService.getItemsQuantity(cart));
    }

}
