package com.internet.shop.controller.orders;

import com.internet.shop.service.OrderService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders/delete")
public class DeleteOrderController extends HttpServlet {
    private static OrderService orderService;

    @Override
    public void init() {
        orderService = (OrderService)
                getServletContext().getAttribute("orderService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        orderService.delete(Long.valueOf(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/orders/all");
    }
}
