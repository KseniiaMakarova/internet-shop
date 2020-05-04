package com.internet.shop.controller.users;

import com.internet.shop.lib.Injector;
import com.internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/users/delete")
public class DeleteUserController extends HttpServlet {
    private static UserService userService;

    @Override
    public void init() {
        Injector injector = (Injector) getServletContext().getAttribute("injector");
        userService =
                (UserService) injector.getInstance(UserService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String userId = req.getParameter("id");
        userService.delete(Long.valueOf(userId));
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
