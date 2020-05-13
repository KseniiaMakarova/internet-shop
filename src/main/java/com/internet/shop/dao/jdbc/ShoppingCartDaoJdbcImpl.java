package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart element) {
        return null;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return null;
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
