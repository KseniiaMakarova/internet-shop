package com.internet.shop.controller.products;

import com.internet.shop.service.ProductService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products/delete")
public class DeleteProductController extends HttpServlet {
    private static ProductService productService;

    @Override
    public void init() {
        productService = (ProductService)
                getServletContext().getAttribute("productService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        productService.delete(Long.valueOf(req.getParameter("id")));
        req.setAttribute("products", productService.getAll());
        resp.sendRedirect(req.getContextPath() + "/products/manage");
    }
}
