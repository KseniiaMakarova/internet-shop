package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Product> products;
    private Long id;
    private User user;

    public Order(User user) {
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
}
