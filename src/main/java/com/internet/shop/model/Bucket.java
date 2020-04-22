package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private static Long idGenerator = 0L;
    private final Long id;
    private User user;
    private List<Item> items;

    public Bucket(User user) {
        id = ++idGenerator;
        items = new ArrayList<>();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
