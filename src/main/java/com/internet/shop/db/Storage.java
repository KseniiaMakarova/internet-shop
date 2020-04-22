package com.internet.shop.db;

import com.internet.shop.model.Bucket;
import com.internet.shop.model.Item;
import com.internet.shop.model.Order;
import com.internet.shop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Item> items = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long itemId = 0L;
    private static Long bucketId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;

    public static void addToList(Item item) {
        item.setId(++itemId);
        items.add(item);
    }

    public static void addToList(Bucket bucket) {
        bucket.setId(++bucketId);
        buckets.add(bucket);
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
