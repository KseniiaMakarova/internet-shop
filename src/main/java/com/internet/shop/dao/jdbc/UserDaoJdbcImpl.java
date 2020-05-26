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
import java.sql.Statement;
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
    public User create(User element) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertUserQuery = "INSERT INTO users (name, login, password, salt) "
                    + "VALUES (?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insertUserQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setBytes(4, element.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
            insertUsersRoles(element, connection);
            LOGGER.info(element + " was created.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return getUserByParameter(
                    "SELECT * FROM users WHERE login = ", "'" + login + "'");
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get user with login " + login, e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        try {
            return getUserByParameter(
                    "SELECT * FROM users WHERE user_id = ", String.valueOf(id));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get user with ID " + id, e);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectAllUsersQuery = "SELECT * FROM users;";
            PreparedStatement statement = connection.prepareStatement(selectAllUsersQuery);
            ResultSet resultSet = statement.executeQuery();
            List<User> allUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId(), connection));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all users", e);
        }
    }

    @Override
    public User update(User element) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateUserQuery = "UPDATE users SET name = ?, login = ?, password = ?, salt = ? "
                    + "WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(updateUserQuery);
            statement.setString(1, element.getName());
            statement.setString(2, element.getLogin());
            statement.setString(3, element.getPassword());
            statement.setBytes(4, element.getSalt());
            statement.setLong(5, element.getId());
            statement.executeUpdate();
            deleteUserFromUsersRoles(element.getId(), connection);
            insertUsersRoles(element, connection);
            LOGGER.info(element + " was updated.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String deleteUserQuery = "DELETE FROM users WHERE user_id = ?;";
            deleteUserFromUsersRoles(id, connection);
            PreparedStatement statement = connection.prepareStatement(deleteUserQuery);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            LOGGER.info("A user with id " + id + " was deleted.");
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete user with ID " + id, e);
        }
    }

    private void insertUsersRoles(User user, Connection connection) throws SQLException {
        String selectRoleIdQuery = "SELECT role_id FROM roles WHERE role_name = ?";
        String insertUsersRolesQuery = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
        PreparedStatement selectStatement =
                connection.prepareStatement(selectRoleIdQuery);
        for (Role role : user.getRoles()) {
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

    private Optional<User> getUserByParameter(String query, String parameter)
            throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + parameter + ";");
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                user.setRoles(getRolesFromUserId(user.getId(), connection));
                return Optional.of(user);
            }
            return Optional.empty();
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        User user = new User(name, login, password);
        user.setId(id);
        user.setSalt(salt);
        return user;
    }

    private Set<Role> getRolesFromUserId(Long userId, Connection connection) throws SQLException {
        String selectRoleNameQuery = "SELECT role_name FROM users_roles "
                + "JOIN roles USING (role_id) WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(selectRoleNameQuery);
        statement.setLong(1, userId);
        ResultSet resultSet = statement.executeQuery();
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            roles.add(Role.of(resultSet.getString("role_name")));
        }
        return roles;
    }

    private void deleteUserFromUsersRoles(Long userId, Connection connection) throws SQLException {
        String deleteUserQuery = "DELETE FROM users_roles WHERE user_id = ?;";
        PreparedStatement statement = connection.prepareStatement(deleteUserQuery);
        statement.setLong(1, userId);
        statement.executeUpdate();
    }
}
