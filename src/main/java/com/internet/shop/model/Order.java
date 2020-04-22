package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Item> items;
    private Long id;
    private User user;

    public Order(User user) {
        items = new ArrayList<>();
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
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
