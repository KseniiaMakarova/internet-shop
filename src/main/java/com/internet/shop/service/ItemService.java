package com.internet.shop.service;

import com.internet.shop.model.Item;
import java.util.List;

public interface ItemService {
    Item create(Item item);

    Item get(Long id);

    List<Item> getAll();

    List<Item> getAllAvailable();

    Item update(Item item);

    boolean delete(Long id);
}
