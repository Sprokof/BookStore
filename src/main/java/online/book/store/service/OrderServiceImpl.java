package online.book.store.service;

import cache.LFUCache;
import cache.LFUCacheSingleton;
import online.book.store.dao.OrderDao;
import online.book.store.entity.*;
import online.book.store.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


@Service
@Component
public class OrderServiceImpl implements OrderService{
    private final LFUCache cache = LFUCacheSingleton.cacheInstance();


    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderDao orderDao;

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

        cache.updateIfExist(getUserData(user), user);
        userService.updateUser(user);

    }

    private void addDetails(Order order, Checkout checkout){
        OrderDetails details = new OrderDetails(checkout.getAddress(),
                checkout.getZip(), deliveryDate(order.getOrderDate(), 3),
                deliveryDate(order.getOrderDate(), 7));
        order.setOrderDetails(details);
    }

    private String deliveryDate(String orderDate, int countDays){
        LocalDate date = parseStringToLocalDate(orderDate);
        return date.plusDays(countDays).toString();
    }

    private void takeFromStock(CartItem item){
        Book book = item.getBook();
        int currentCopies = book.getAvailableCopies();
        int quantity = item.getQuantity();
        book.setAvailableCopies((currentCopies - quantity));
        this.bookService.updateBook(book);
    }

    @Override
    public List<Order> getSortedOrders (List<Order> orders) {
        orders.forEach(this::setOrderStatus);
        orders.sort((o1, o2) -> compareOrderDates(o1.getOrderDate(), o2.getOrderDate()));
        return orders;
    }

    private int compareOrderDates (String dateOne, String dateTwo){
        int compareResult = 0;
        if(Date.valueOf(dateOne).after(Date.valueOf(dateTwo))){
            compareResult = - 1;
        }
        else if(Date.valueOf(dateOne).before(Date.valueOf(dateTwo))){
            compareResult = 1;
        }
        return compareResult;
    }

    @Override
    public void deleteDeliveredOrders(User user) {
        String currentDate = LocalDate.now().toString();
        String statement = "DELETE FROM ORDERS_DETAILS WHERE " +
                "cast(LAST_DELIVERY_DATE as date) <= " + "cast('" + currentDate + "' as date)";
        this.orderDao.deleteOrders(statement);
        cache.updateIfExist(getUserData(user), user);
    }

    private void setOrderStatus(Order order){
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDate = parseStringToLocalDate(order.getOrderDetails().
                getFirstDeliveryDate());
        if(firstDate.minusDays(1).equals(currentDate)){
            order.setStatus(OrderStatus.IN_DELIVERY);
        }

        else if(firstDate.plusDays(3).equals(currentDate)){
            order.setStatus(OrderStatus.DELIVERED);
        }

        this.orderDao.updateOrder(order);
    }

    private LocalDate parseStringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formatter = formatter.withLocale(Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }

    private String[] getUserData(User user){
        return new String[]{user.getUsername(), user.getEmail()};
    }
}
