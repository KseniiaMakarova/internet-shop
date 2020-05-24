package com.internet.shop.dao;

import com.internet.shop.model.Order;
import com.internet.shop.model.User;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(User user);
}
