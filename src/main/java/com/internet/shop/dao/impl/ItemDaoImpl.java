package com.internet.shop.dao.impl;

import com.internet.shop.dao.ItemDao;
import com.internet.shop.dao.Storage;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Item;
import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }

    @Override
    public Item update(Item item) {
        if (Storage.items.contains(item)) {
            Storage.items.set(Storage.items.indexOf(item), item);
        } else {
            create(item);
        }
        return item;
    }

    @Override
    public boolean delete(Long id) {
        Item item = Storage.items.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
        return Storage.items.remove(item);
    }
}
