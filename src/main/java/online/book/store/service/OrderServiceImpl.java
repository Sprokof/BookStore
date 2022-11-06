package online.book.store.service;

import online.book.store.entity.*;
import online.book.store.status.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


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
            addDetails(order, user.getCheckout());
            takeFromStock(item);
            user.addOrder(order);
        });

        userService.updateUser(user);

    }

    private void addDetails(Order order, Checkout checkout){
        OrderDetails details = new OrderDetails();
        details.setAddress(checkout.getAddress());
        details.setZip(checkout.getZip());
        details.setDeliveryDatesInterval(generateDeliveryInterval(order.getOrderDate()));
        order.setOrderDetails(details);
    }

    private String generateDeliveryInterval(String orderDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        LocalDate date = LocalDate.parse(orderDate, formatter);
        return date.plusDays(3) + " - " + date.plusDays(7);
    }

    private void takeFromStock(CartItem item){
        Book book = item.getBook();
        int currentCopies = book.getAvailableCopies();
        int quantity = item.getQuantity();
        book.setAvailableCopies((currentCopies - quantity));
        this.bookService.updateBook(book);
    }


}
