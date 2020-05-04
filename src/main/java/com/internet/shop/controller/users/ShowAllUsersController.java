package com.internet.shop.controller.users;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/all")
public class ShowAllUsersController extends HttpServlet {
    private static UserService userService;

    @Override
    public void init() {
        Injector injector = (Injector) getServletContext().getAttribute("injector");
        userService =
                (UserService) injector.getInstance(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> users = userService.getAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/views/users/all.jsp").forward(req, resp);
    }
}
