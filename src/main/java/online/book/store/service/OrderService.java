package online.book.store.service;

import online.book.store.entity.Order;
import online.book.store.entity.User;

import java.util.List;

public interface OrderService {
    void addOrders(User user);
    List<Order> getSortedOrders(List<Order> orders);
    void deleteDeliveredOrders();
}
