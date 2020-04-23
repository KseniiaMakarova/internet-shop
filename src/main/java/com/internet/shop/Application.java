package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product jeans = new Product("jeans", 45.00);
        Product boots = new Product("boots", 80.00);
        Product jacket = new Product("jacket", 69.00);

        productService.create(jeans);
        productService.create(jacket);
        productService.create(boots);
        System.out.println(productService.get(boots.getId()));

        System.out.println(productService.getAll());

        System.out.println(productService.delete(jacket.getId()));
        Product cap = new Product("cap", 20.00);
        System.out.println(productService.delete(cap.getId()));

        System.out.println(productService.getAll());
        jeans.setPrice(70.50);
        jeans.setName("new jeans");
        productService.update(jeans);
        System.out.println(productService.getAll());
    }
}
