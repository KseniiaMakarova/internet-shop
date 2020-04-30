package com.internet.shop.security;

import com.internet.shop.exception.AuthenticationException;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private UserService userService;

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.findByLogin(login).orElseThrow(
                () -> new AuthenticationException("This login does not exist. Please try again."));
        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("The password is incorrect. Please try again.");
        }
        return user;
    }
}
