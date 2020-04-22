package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Item;
import com.internet.shop.service.ItemService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ItemService itemService = (ItemService) injector.getInstance(ItemService.class);
        Item jeans = new Item("jeans", 45.00, 20);
        Item boots = new Item("boots", 80.00, 5);
        Item jacket = new Item("jacket", 69.00, 7);

        itemService.create(jeans);
        itemService.create(jacket);
        itemService.create(boots);
        System.out.println(itemService.get(boots.getId()));

        jacket.setCount(0);
        System.out.println(itemService.getAll());
        System.out.println(itemService.getAllAvailable());

        System.out.println(itemService.delete(jacket.getId()));
        Item cap = new Item("cap", 20.00, 10);
        System.out.println(itemService.delete(cap.getId()));

        System.out.println(itemService.getAll());
        jeans.setPrice(70.50);
        jeans.setName("new jeans");
        itemService.update(jeans);
        System.out.println(itemService.getAll());
    }
}
