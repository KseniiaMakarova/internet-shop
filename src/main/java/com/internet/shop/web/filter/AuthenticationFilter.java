package com.internet.shop.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationFilter implements Filter {
    private static final String USER_ID = "userId";
    private static List<String> excludedServlets;

    @Override
    public void init(FilterConfig filterConfig) {
        String excluded = filterConfig.getInitParameter("excluded");
        excludedServlets = Arrays.asList(excluded.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String servletPath = req.getServletPath();
        if (!excludedServlets.contains(servletPath)
                && req.getSession().getAttribute(USER_ID) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}
