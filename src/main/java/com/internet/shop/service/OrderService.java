package com.internet.shop.service;

import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import java.util.List;

public interface OrderService {
    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);

    Order get(Long id);

    List<Order> getAll();

    boolean delete(Long id);
}
