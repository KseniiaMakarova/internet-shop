package com.internet.shop.controller;

import com.internet.shop.exception.AuthenticationException;
import com.internet.shop.lib.Injector;
import com.internet.shop.model.User;
import com.internet.shop.security.AuthenticationService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final AuthenticationService authenticationService =
            (AuthenticationService) INJECTOR.getInstance(AuthenticationService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        try {
            String password = req.getParameter("psw");
            User user = authenticationService.login(login, password);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("userId", user.getId());
            resp.sendRedirect(req.getContextPath() + "/");
        } catch (AuthenticationException exception) {
            LOGGER.warn("Authentication failed for user " + login
                    + " - " + exception.getMessage());
            req.setAttribute("message", exception.getMessage());
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}
