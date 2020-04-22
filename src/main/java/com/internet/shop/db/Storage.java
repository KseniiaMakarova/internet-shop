package com.internet.shop.db;

import com.internet.shop.model.Item;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Item> items = new ArrayList<>();
    private static Long itemId = 0L;
    private static Long bucketId = 0L;
    private static Long orderId = 0L;
    private static Long userId = 0L;

    public static void addToList(Item item) {
        item.setId(++itemId);
        items.add(item);
    }
}
