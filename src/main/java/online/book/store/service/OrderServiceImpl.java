package online.book.store.service;

import online.book.store.entity.CartItem;
import online.book.store.entity.Order;
import online.book.store.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Component
public class OrderServiceImpl implements OrderService{

    @Autowired
    private UserService userService;

    @Autowired
    private SignInService signInService;

    @Override
    public void addOrders() {
        User savedUser = signInService.getSavedUser();
        List<CartItem> userItems = savedUser.getCart().getCartItems();


        userItems.forEach((item) -> {
            Order order = new Order(item.getBook().getTitle(), item.getQuantity(), item.getTotal(),
                    item.getBook().getBookImageName());
            order.setStatus("Paid");
            savedUser.addOrder(order);
        });

        userService.updateUserInSession(savedUser);

    }
}
