package online.book.store.controllers;

import online.book.store.entity.Order;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    HttpSession httpSession;

    @ModelAttribute("orders")
    public List<Order> getOrders(){
        return ((User) httpSession.getAttribute("user")).getOrders();
    }

    @GetMapping("/home/orders")
    public String orders(){
        return "orders";
    }
}
