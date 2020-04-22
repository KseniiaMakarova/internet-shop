package com.internet.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
    private List<Item> items;
    private Long id;
    private User user;

    public Bucket(User user) {
        items = new ArrayList<>();
        this.user = user;
    }
}
