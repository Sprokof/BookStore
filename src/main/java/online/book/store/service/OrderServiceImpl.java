package online.book.store.service;

import online.book.store.entity.CartItem;
import online.book.store.entity.Order;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.List;


@Component
public class OrderServiceImpl implements OrderService{

    @Autowired
    HttpSession httpSession;

    @Autowired
    UserService userService;

    @Override
    public void addOrders() {
        User user = (User) httpSession.getAttribute("user");
        List<CartItem> userItems = user.getCart().getCartItems();


        userItems.forEach((item) -> {
            Order order = new Order(item.getBook().getTitle(), item.getQuantity(), item.getTotal(),
                    item.getBook().getPath());
            order.setStatus("Paid");
            user.addOrder(order);
        });

        httpSession.setAttribute("user", user);

        userService.updateUser(user);

    }
}
