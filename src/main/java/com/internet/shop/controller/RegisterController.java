package com.internet.shop.controller;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.model.User;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private static UserService userService;
    private static ShoppingCartService shoppingCartService;

    @Override
    public void init() {
        Injector injector = (Injector) getServletContext().getAttribute("injector");
        userService =
                (UserService) injector.getInstance(UserService.class);
        shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
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
            req.setAttribute("message", "Your passwords are different. Please try again.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}
