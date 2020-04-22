package com.internet.shop.service.impl;

import com.internet.shop.dao.ItemDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.Item;
import com.internet.shop.service.ItemService;
import java.util.List;

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
        return itemDao.get(id).get();
    }

    @Override
    public List<Item> getAll() {
        return itemDao.getAll();
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
