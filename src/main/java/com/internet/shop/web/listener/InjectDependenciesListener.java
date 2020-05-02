package com.internet.shop.web.listener;

import com.internet.shop.lib.Injector;
import com.internet.shop.security.AuthenticationService;
import com.internet.shop.service.OrderService;
import com.internet.shop.service.ProductService;
import com.internet.shop.service.ShoppingCartService;
import com.internet.shop.service.UserService;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class InjectDependenciesListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Injector injector = Injector.getInstance("com.internet.shop");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("authenticationService",
                injector.getInstance(AuthenticationService.class));
        servletContext.setAttribute("productService",
                injector.getInstance(ProductService.class));
        servletContext.setAttribute("userService",
                injector.getInstance(UserService.class));
        servletContext.setAttribute("orderService",
                injector.getInstance(OrderService.class));
        servletContext.setAttribute("shoppingCartService",
                injector.getInstance(ShoppingCartService.class));
    }
}
