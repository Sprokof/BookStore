package online.book.store.dao;

import online.book.store.entity.Order;

public interface OrderDao {
    void deleteOrders(String statement);
    void updateOrder(Order order);
}
