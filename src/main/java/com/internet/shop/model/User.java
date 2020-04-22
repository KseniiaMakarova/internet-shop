package com.internet.shop.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private static Long idGenerator = 0L;
    private final Long id;
    private final Set<Role> roles;
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) {
        id = ++idGenerator;
        roles = new HashSet<>();
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private static class Role {
        Long id;
        String roleName;

        Role(Long id, String roleName) {
            this.id = id;
            this.roleName = roleName;
        }
    }
}
