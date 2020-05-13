package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.model.Order;
import java.util.List;
import java.util.Optional;

public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order element) {
        return null;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order update(Order element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
