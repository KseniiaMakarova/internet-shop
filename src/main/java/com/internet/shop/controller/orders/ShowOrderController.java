package com.internet.shop.controller.orders;

import com.internet.shop.model.Order;
import com.internet.shop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders/show")
public class ShowOrderController extends HttpServlet {
    private static OrderService orderService;

    @Override
    public void init() {
        orderService = (OrderService)
                getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Order order = orderService.get(Long.valueOf(req.getParameter("id")));
        req.setAttribute("order", order);
        req.getRequestDispatcher("/views/orders/show.jsp").forward(req, resp);
    }
}
