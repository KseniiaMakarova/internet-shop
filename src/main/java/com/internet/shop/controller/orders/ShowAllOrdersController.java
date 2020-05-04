package com.internet.shop.controller.orders;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders/all")
public class ShowAllOrdersController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static OrderService orderService;
    private static UserService userService;

    @Override
    public void init() {
        Injector injector = (Injector) getServletContext().getAttribute("injector");
        orderService =
                (OrderService) injector.getInstance(OrderService.class);
        userService =
                (UserService) injector.getInstance(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> orders = orderService.getUserOrders(userService.get(USER_ID));
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("/views/orders/all.jsp").forward(req, resp);
    }
}
