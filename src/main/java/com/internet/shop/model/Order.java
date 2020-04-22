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
}
