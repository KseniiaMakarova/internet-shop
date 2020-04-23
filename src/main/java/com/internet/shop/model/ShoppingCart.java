package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCart {
    private Long id;
    private User user;
    private List<Product> products;

    public ShoppingCart(User user) {
        products = new ArrayList<>();
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" + "id=" + id
                + ", user=" + user.getName()
                + ", products=" + products.stream()
                                    .map(Product::getName)
                                    .collect(Collectors.toList()) + '}';
    }
}
