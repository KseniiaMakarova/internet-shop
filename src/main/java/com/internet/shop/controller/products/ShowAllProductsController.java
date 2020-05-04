package com.internet.shop.controller.products;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products/all")
public class ShowAllProductsController extends HttpServlet {
    private static ProductService productService;

    @Override
    public void init() {
        Injector injector = (Injector) getServletContext().getAttribute("injector");
        productService =
                (ProductService) injector.getInstance(ProductService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Product> products = productService.getAll();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/views/products/all.jsp").forward(req, resp);
    }
}
