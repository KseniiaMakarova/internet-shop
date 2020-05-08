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
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long orderId = Long.valueOf(req.getParameter("id"));
        Long userId = orderService.get(orderId).getUserId();
        orderService.delete(orderId);
        resp.sendRedirect(req.getContextPath() + "/orders/all-particular-user?id=" + userId);
    }
}
