package com.internet.shop.controllers.users;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("psw");
        String repeatedPassword = req.getParameter("rep-psw");
        if (password.equals(repeatedPassword)) {
            User user = userService.create(new User(name, login, password));
            shoppingCartService.create(new ShoppingCart(user));
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Your repeated password was different. Try again.");
            req.getRequestDispatcher("/WEB-INF/views/users/register.jsp").forward(req, resp);
        }
    }
}