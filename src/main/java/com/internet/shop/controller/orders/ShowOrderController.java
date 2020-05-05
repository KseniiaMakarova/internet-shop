package com.internet.shop.controller.orders;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/orders/show")
public class ShowOrderController extends HttpServlet {
    private static final String USER_ID = "userId";
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Order order = orderService.get(Long.valueOf(req.getParameter("id")));
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        User user = userService.get(userId);
        req.setAttribute("order", order);
        req.setAttribute("isAdmin",
                user.getRoles().stream()
                        .anyMatch(role -> role.getRoleName().equals(Role.RoleName.ADMIN)));
        req.getRequestDispatcher("/views/orders/show.jsp").forward(req, resp);
    }
}
