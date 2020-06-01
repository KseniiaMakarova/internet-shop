package com.internet.shop.controller.orders;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Role;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/order/info")
public class ShowOrderController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ShowOrderController.class);
    private static final String USER_ID = "userId";
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long viewingUserId = (Long) req.getSession().getAttribute(USER_ID);
        Order order = orderService.get(Long.valueOf(req.getParameter("id")));
        if (isUserAuthorized(viewingUserId, order)) {
            req.setAttribute("order", order);
            req.getRequestDispatcher("/views/orders/info.jsp").forward(req, resp);
        } else {
            LOGGER.log(Level.WARN,
                    "User with ID {} tried to get access to the order of another user",
                    viewingUserId);
            req.getRequestDispatcher("/views/accessDenied.jsp").forward(req, resp);
        }
    }

    private boolean isUserAuthorized(Long viewingUserId, Order order) {
        return order.getUserId().equals(viewingUserId)
                || userService.get(viewingUserId).getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(Role.RoleName.ADMIN));
    }
}
