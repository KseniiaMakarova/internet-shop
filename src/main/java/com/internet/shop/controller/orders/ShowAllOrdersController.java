package com.internet.shop.controller.orders;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
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
    private static final String USER_ID = "userId";
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = req.getParameter("id") == null
                ? (Long) req.getSession().getAttribute(USER_ID)
                : Long.valueOf(req.getParameter("id"));
        User user = userService.get(userId);
        List<Order> orders = orderService.getUserOrders(user);
        req.setAttribute("orders", orders);
        req.setAttribute("isAdmin",
                user.getRoles().stream()
                        .anyMatch(role -> role.getRoleName().equals(Role.RoleName.ADMIN)));
        req.getRequestDispatcher("/views/orders/all.jsp").forward(req, resp);
    }
}
