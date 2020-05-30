package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.OrderDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Order;
import com.internet.shop.model.Product;
import com.internet.shop.model.User;
import com.internet.shop.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final Logger LOGGER = LogManager.getLogger(OrderDaoJdbcImpl.class);

    @Override
    public Order create(Order element) {
        String insertOrderQuery = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(insertOrderQuery,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, element.getUserId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                element.setId(resultSet.getLong(1));
                insertOrdersProducts(element, connection);
                LOGGER.info(element + " was created.");
                return element;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String selectOrderQuery = "SELECT * FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(selectOrderQuery)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Order order = getOrderFromResultSet(resultSet, connection);
                    return Optional.of(order);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get order with ID " + id, e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) {
        String selectAllOrdersQuery = "SELECT * FROM orders WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(selectAllOrdersQuery)) {
            statement.setLong(1, user.getId());
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Order> allOrders = new ArrayList<>();
                while (resultSet.next()) {
                    Order order = getOrderFromResultSet(resultSet, connection);
                    allOrders.add(order);
                }
                return allOrders;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all orders of user " + user, e);
        }
    }

    @Override
    public List<Order> getAll() {
        String selectAllOrdersQuery = "SELECT * FROM orders;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(selectAllOrdersQuery);
                ResultSet resultSet = statement.executeQuery()) {
            List<Order> allOrders = new ArrayList<>();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet, connection);
                allOrders.add(order);
            }
            return allOrders;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all orders", e);
        }
    }

    @Override
    public Order update(Order element) {
        String updateOrderQuery = "UPDATE orders SET user_id = ? WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(updateOrderQuery)) {
            statement.setLong(1, element.getUserId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
            deleteOrderFromOrdersProducts(element.getId(), connection);
            insertOrdersProducts(element, connection);
            LOGGER.info(element + " was updated.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String deleteOrderQuery = "DELETE FROM orders WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement
                        = connection.prepareStatement(deleteOrderQuery)) {
            deleteOrderFromOrdersProducts(id,connection);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            LOGGER.info("An order with id " + id + " was deleted.");
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete order with ID " + id, e);
        }
    }

    private void insertOrdersProducts(Order order, Connection connection) throws SQLException {
        String insertOrdersProductsQuery
                = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?);";
        try (PreparedStatement insertStatement
                     = connection.prepareStatement(insertOrdersProductsQuery)) {
            for (Product product : order.getProducts()) {
                insertStatement.setLong(1, order.getId());
                insertStatement.setLong(2, product.getId());
                insertStatement.executeUpdate();
            }
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet, Connection connection)
            throws SQLException {
        Long id = resultSet.getLong("order_id");
        Long userId = resultSet.getLong("user_id");
        Order order = new Order(getProductsFromOrderId(id, connection), userId);
        order.setId(id);
        return order;
    }

    private List<Product> getProductsFromOrderId(Long orderId, Connection connection)
            throws SQLException {
        String selectProductIdQuery = "SELECT products.* FROM orders_products "
                + "JOIN products USING (product_id) WHERE order_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(selectProductIdQuery)) {
            statement.setLong(1, orderId);
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (resultSet.next()) {
                    Long id = resultSet.getLong("product_id");
                    String name = resultSet.getString("name");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    boolean available = resultSet.getBoolean("available");
                    Product product = new Product(name, price, available);
                    product.setId(id);
                    products.add(product);
                }
                return products;
            }
        }
    }

    private void deleteOrderFromOrdersProducts(Long orderId, Connection connection)
            throws SQLException {
        String deleteOrderQuery = "DELETE FROM orders_products WHERE order_id = ?;";
        try (PreparedStatement statement
                     = connection.prepareStatement(deleteOrderQuery)) {
            statement.setLong(1, orderId);
            statement.executeUpdate();
        }
    }
}
