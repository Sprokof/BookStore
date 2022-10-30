package online.book.store.service;

import online.book.store.entity.Book;
import online.book.store.entity.CartItem;
import online.book.store.entity.Order;
import online.book.store.entity.User;
import online.book.store.status.OrderStatus;
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

    @Autowired
    private BookService bookService;

    @Override
    public void addOrders(User user) {
        List<CartItem> userItems = user.getCart().getCartItems();

        userItems.forEach((item) -> {
            Order order = new Order(item.getBook().getTitle(), item.getQuantity(), item.getTotal(),
                    item.getBook().getBookImageName());
            order.setStatus(OrderStatus.PAID);
            user.addOrder(order);
        });

        userService.updateUser(user);

    }



}
