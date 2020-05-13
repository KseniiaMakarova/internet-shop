package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.UserDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Role;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoJdbcImpl.class);

    @Override
    public Optional<User> findByLogin(String login) {
        String selectUserQuery = "SELECT * FROM users WHERE login = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectUserQuery);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId()));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get user with login " + login, e);
        }
    }

    @Override
    public User create(User element) {
        String insertUserQuery = "INSERT INTO users (name, login, password) VALUES (?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertUserQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
            insertUsersRoles(element);
            LOGGER.info(element + " was created.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String selectUserQuery = "SELECT * FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectUserQuery);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(id));
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get user with ID " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        String selectAllUsersQuery = "SELECT * FROM users;";
        List<User> allUsers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectAllUsersQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId()));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all users", e);
        }
    }

    @Override
    public User update(User element) {
        String updateUserQuery = "UPDATE users SET name = ?, login = ?, password = ? "
                + "WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateUserQuery);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setLong(4, element.getId());
            statement.executeUpdate();
            deleteUserFromUsersRoles(element.getId());
            insertUsersRoles(element);
            LOGGER.info(element + " was updated.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteUserQuery = "DELETE FROM users WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            deleteUserFromUsersRoles(id);
            PreparedStatement statement = connection.prepareStatement(deleteUserQuery);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            LOGGER.info("A product with id " + id + " was deleted.");
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete user with ID " + id, e);
        }
    }

    private void insertUsersRoles(User user) throws SQLException {
        String selectRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String insertUsersRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Role role : user.getRoles()) {
                PreparedStatement selectStatement =
                        connection.prepareStatement(selectRoleIdQuery);
                selectStatement.setString(1, role.getRoleName().name());
                ResultSet resultSet = selectStatement.executeQuery();
                resultSet.next();
                PreparedStatement insertStatement =
                        connection.prepareStatement(insertUsersRolesQuery);
                insertStatement.setLong(1, user.getId());
                insertStatement.setLong(2, resultSet.getLong("role_id"));
                insertStatement.executeUpdate();
            }
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        User user = new User(name, login, password);
        user.setId(id);
        return user;
    }

    private Set<Role> getRolesFromUserId(Long userId) throws SQLException {
        String selectRoleNameQuery = "SELECT role_name FROM users_roles "
                + "JOIN roles USING (role_id) WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectRoleNameQuery);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                roles.add(Role.of(resultSet.getString("role_name")));
            }
            return roles;
        }
    }

    private void deleteUserFromUsersRoles(Long userId) throws SQLException {
        String deleteUserQuery = "DELETE FROM users_roles WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteUserQuery);
            statement.setLong(1, userId);
            statement.executeUpdate();
        }
    }
}
