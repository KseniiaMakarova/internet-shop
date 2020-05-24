package com.internet.shop.controller.users;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Order;
import com.internet.shop.model.Role;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/delete")
public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private static final String USER_ID = "userId";
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Long userId = Long.valueOf(req.getParameter("id"));
        if (userId.equals(req.getSession().getAttribute(USER_ID))) {
            req.getRequestDispatcher("/views/accessDenied.jsp").forward(req, resp);
        } else {
            User user = userService.get(userId);
            if (user.getRoles().stream()
                    .noneMatch(role -> role.getRoleName().equals(Role.RoleName.ADMIN))) {
                ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
                shoppingCartService.delete(shoppingCart.getId());
                List<Order> orders = orderService.getUserOrders(user);
                for (Order order : orders) {
                    orderService.delete(order.getId());
                }
            }
            userService.delete(userId);
            resp.sendRedirect(req.getContextPath() + "/users/all");
        }
    }
}
