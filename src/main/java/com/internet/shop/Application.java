package com.internet.shop;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("com.internet.shop");

    public static void main(String[] args) {
        final ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);
        final UserService userService =
                (UserService) injector.getInstance(UserService.class);
        final ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        final OrderService orderService =
                (OrderService) injector.getInstance(OrderService.class);

        Product flowers = new Product("flowers", 250.00);
        Product boots = new Product("boots", 1500.00);
        Product puppy = new Product("black puppy", 1000.00);

        productService.create(flowers);
        productService.create(boots);
        productService.create(puppy);

        System.out.println(productService.get(boots.getId()));
        System.out.println(productService.getAll());

        Product beer = new Product("beer", 20.00);
        System.out.println(productService.delete(beer.getId()));

        puppy.setName("white puppy");
        productService.update(puppy);
        System.out.println(productService.getAll());

        User arthur = new User("Arthur", "Arcana Lord", "123123123");
        User peter = new User("Peter", "Chris Wilson", "987654321");
        userService.create(arthur);
        userService.create(peter);
        System.out.println(userService.get(arthur.getId()));
        System.out.println(userService.getAll());

        arthur.setLogin("Galahat");
        userService.update(arthur);
        System.out.println(userService.get(arthur.getId()));

        userService.delete(peter.getId());
        System.out.println(userService.getAll());

        ShoppingCart arthurCart = shoppingCartService.getByUserId(arthur.getId());
        shoppingCartService.addProduct(arthurCart, puppy);
        shoppingCartService.addProduct(arthurCart, flowers);
        shoppingCartService.addProduct(arthurCart, boots);
        System.out.println(shoppingCartService.getAllProducts(arthurCart));

        shoppingCartService.deleteProduct(arthurCart, boots);
        System.out.println(shoppingCartService.getByUserId(arthur.getId()));

        Order arthurOrder = orderService.completeOrder(
                shoppingCartService.getAllProducts(arthurCart), arthur);
        System.out.println(shoppingCartService.getByUserId(arthur.getId()));

        System.out.println(orderService.get(arthurOrder.getId()));
        System.out.println(orderService.getUserOrders(arthur));

        userService.create(peter);
        productService.create(beer);
        ShoppingCart peterCart = shoppingCartService.getByUserId(peter.getId());
        shoppingCartService.addProduct(peterCart, beer);
        Order peterOrder = orderService.completeOrder(
                shoppingCartService.getAllProducts(peterCart), peter);

        System.out.println(orderService.getAll());
        orderService.delete(peterOrder.getId());
        System.out.println(orderService.getAll());
    }
}
