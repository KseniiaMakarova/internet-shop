package com.internet.shop.dao;

import com.internet.shop.model.Item;
import java.util.List;
import java.util.Optional;

public interface ItemDao {
    Item create(Item item);

    Optional<Item> get(Long id);

    List<Item> getAll();

    Item update(Item item);

    boolean delete(Long id);
}
