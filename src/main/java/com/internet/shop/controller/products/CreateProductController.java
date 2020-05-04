package com.internet.shop.controller.products;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Product;
import com.internet.shop.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products/manage")
public class CreateProductController extends HttpServlet {
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
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/views/products/manage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        productService.create(new Product(name, Double.parseDouble(price)));
        req.setAttribute("products", productService.getAll());
        req.getRequestDispatcher("/views/products/manage.jsp").forward(req, resp);
    }
}
