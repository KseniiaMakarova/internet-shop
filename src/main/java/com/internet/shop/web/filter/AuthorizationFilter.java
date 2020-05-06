package com.internet.shop.web.filter;

import com.internet.shop.lib.Injector;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);
    private static final String USER_ID = "userId";
    private static final Injector INJECTOR = Injector.getInstance("com.internet.shop");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);
    private final Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        protectedUrls.put("/products/all", List.of(Role.RoleName.USER));
        protectedUrls.put("/products/add-to-cart", List.of(Role.RoleName.USER));
        protectedUrls.put("/products/remove-from-cart", List.of(Role.RoleName.USER));
        protectedUrls.put("/cart/show", List.of(Role.RoleName.USER));
        protectedUrls.put("/orders/complete", List.of(Role.RoleName.USER));
        protectedUrls.put("/orders/all-my", List.of(Role.RoleName.USER));
        protectedUrls.put("/users/all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/users/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/manage", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/delete", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/all-particular-user", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/orders/delete", List.of(Role.RoleName.ADMIN));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String servletPath = req.getServletPath();
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        if (!protectedUrls.containsKey(servletPath)
                || isUserAuthorized(userService.get(userId), protectedUrls.get(servletPath))) {
            filterChain.doFilter(req, resp);
        } else {
            LOGGER.info("User with ID " + userId + " tried to access a secure resource.");
            req.getRequestDispatcher("/views/accessDenied.jsp").forward(req, resp);
        }
    }

    private boolean isUserAuthorized(User user, List<Role.RoleName> authorizedRoles) {
        for (Role userRole : user.getRoles()) {
            for (Role.RoleName authorizedRole : authorizedRoles) {
                if (userRole.getRoleName().equals(authorizedRole)) {
                    return true;
                }
            }
        }
        return false;
    }
}
