package com.internet.shop.controller.cart;

import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products/add-to-cart")
public class AddProductToCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static ProductService productService;
    private static ShoppingCartService shoppingCartService;

    @Override
    public void init() {
        productService = (ProductService)
                getServletContext().getAttribute("productService");
        shoppingCartService = (ShoppingCartService)
                getServletContext().getAttribute("shoppingCartService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String productId = req.getParameter("id");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        Product product = productService.get(Long.valueOf(productId));
        shoppingCartService.addProduct(shoppingCart, product);
        resp.sendRedirect(req.getContextPath() + "/products/all");

    }
}
