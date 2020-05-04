package com.internet.shop.controller.orders;

import com.internet.shop.lib.Injector;
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
        Injector injector = (Injector) getServletContext().getAttribute("injector");
        orderService =
                (OrderService) injector.getInstance(OrderService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        orderService.delete(Long.valueOf(req.getParameter("id")));
        resp.sendRedirect(req.getContextPath() + "/orders/all");
    }
}
