package com.internet.shop.db;

import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long productId = 0L;
    private static Long shoppingCartId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;

    private Storage() {

    }

    public static void addToList(Product product) {
        product.setId(++productId);
        products.add(product);
    }

    public static void addToList(ShoppingCart shoppingCart) {
        shoppingCart.setId(++shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void addToList(Order order) {
        order.setId(++orderId);
        orders.add(order);
    }

    public static void addToList(User user) {
        user.setId(++userId);
        users.add(user);
    }
}
