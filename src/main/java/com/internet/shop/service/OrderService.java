package com.internet.shop.service;

import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);
}
