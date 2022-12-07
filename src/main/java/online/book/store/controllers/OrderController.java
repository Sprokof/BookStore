package online.book.store.controllers;

import online.book.store.dto.OrderDto;
import online.book.store.entity.Order;
import online.book.store.entity.User;
import online.book.store.service.OrderService;
import online.book.store.service.SessionService;
import online.book.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @GetMapping("/orders")
    public String orders(@RequestParam ("user") String login, Model model){
        this.orderService.deleteDeliveredOrders();
        User user = this.userService.getUserByLogin(login);
        List<Order> userOrders = user.getOrders();
        if(userOrders.isEmpty()){
            return "result";
        }
        model.addAttribute("orders", orderService.getSortedOrders(userOrders));
        return "orders";
    }


    @PostMapping("/orders/add")
    public ResponseEntity<HttpStatus> addOrder(@RequestBody OrderDto orderDto){
        String sessionid = orderDto.getSessionid();
        User user = sessionService.getCurrentUser(sessionid);
        orderService.addOrders(user);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

}
