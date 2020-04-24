package com.internet.shop.dao;

import com.internet.shop.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);

    Optional<User> get(Long id);

    List<User> getAll();

    User update(User user);

    boolean delete(Long id);
}
