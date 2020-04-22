package com.internet.shop.service.impl;

import com.internet.shop.dao.ItemDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Item;
import com.internet.shop.service.ItemService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Inject
    private ItemDao itemDao;

    @Override
    public Item create(Item item) {
        return itemDao.create(item);
    }

    @Override
    public Item get(Long id) {
        return itemDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can not find item with ID " + id));
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
    }

    @Override
    public List<Item> getAllAvailable() {
        return itemDao.getAll().stream()
                .filter(i -> i.getCount() > 0)
                .collect(Collectors.toList());
    }

    @Override
    public Item update(Item item) {
        return itemDao.update(item);
    }

    @Override
    public boolean delete(Long id) {
        return itemDao.delete(id);
    }
}
