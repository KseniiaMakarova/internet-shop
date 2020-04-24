package com.internet.shop.service;

import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);

    List<Product> getAllProducts(ShoppingCart shoppingCart);
}
