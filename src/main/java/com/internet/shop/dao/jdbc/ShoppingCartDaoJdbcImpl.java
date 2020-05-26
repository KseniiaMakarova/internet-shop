package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ShoppingCartDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.model.ShoppingCart;
import com.internet.shop.util.ConnectionUtil;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    private static final Logger LOGGER = LogManager.getLogger(ShoppingCartDaoJdbcImpl.class);

    @Override
    public ShoppingCart create(ShoppingCart element) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertShoppingCartQuery = "INSERT INTO carts (user_id) VALUES (?);";
            PreparedStatement statement = connection.prepareStatement(insertShoppingCartQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
            insertCartsProducts(element, connection);
            LOGGER.info(element + " was created.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        try {
            return getShoppingCartByParameter(
                    "SELECT * FROM carts WHERE cart_id = ", String.valueOf(id));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get cart with ID " + id, e);
        }
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        try {
            return getShoppingCartByParameter(
                    "SELECT * FROM carts WHERE user_id = ", String.valueOf(userId));
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get cart of user with ID " + userId, e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectAllShoppingCartsQuery = "SELECT * FROM carts;";
            PreparedStatement statement = connection.prepareStatement(selectAllShoppingCartsQuery);
            ResultSet resultSet = statement.executeQuery();
            List<ShoppingCart> allShoppingCarts = new ArrayList<>();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(
                        getProductsFromShoppingCartId(shoppingCart.getId(), connection));
                allShoppingCarts.add(shoppingCart);
            }
            return allShoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateShoppingCartQuery = "UPDATE carts SET user_id = ? "
                    + "WHERE cart_id = ?;";
            PreparedStatement statement = connection.prepareStatement(updateShoppingCartQuery);
            statement.setLong(1, element.getUserId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
            deleteShoppingCartFromCartsProducts(element.getId(), connection);
            insertCartsProducts(element, connection);
            LOGGER.info(element + " was updated.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String deleteShoppingCartQuery = "DELETE FROM carts WHERE cart_id = ?;";
            deleteShoppingCartFromCartsProducts(id, connection);
            PreparedStatement statement = connection.prepareStatement(deleteShoppingCartQuery);
            statement.setLong(1, id);
            int numberOfRowsDeleted = statement.executeUpdate();
            LOGGER.info("A cart with id " + id + " was deleted.");
            return numberOfRowsDeleted != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete cart with ID " + id, e);
        }
    }

    private void insertCartsProducts(ShoppingCart shoppingCart, Connection connection)
            throws SQLException {
        String insertCartsProductsQuery = "INSERT INTO carts_products (cart_id, product_id) "
                + "VALUES (?, ?);";
        PreparedStatement insertStatement =
                connection.prepareStatement(insertCartsProductsQuery);
        for (Product product : shoppingCart.getProducts()) {
            insertStatement.setLong(1, shoppingCart.getId());
            insertStatement.setLong(2, product.getId());
            insertStatement.executeUpdate();
        }
    }

    private Optional<ShoppingCart> getShoppingCartByParameter(String query, String parameter)
            throws SQLException {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query + parameter + ";");
            if (resultSet.next()) {
                ShoppingCart shoppingCart = getShoppingCartFromResultSet(resultSet);
                shoppingCart.setProducts(
                        getProductsFromShoppingCartId(shoppingCart.getId(), connection));
                return Optional.of(shoppingCart);
            }
            return Optional.empty();
        }
    }

    private ShoppingCart getShoppingCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("cart_id");
        Long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userId);
        shoppingCart.setId(id);
        return shoppingCart;
    }

    private List<Product> getProductsFromShoppingCartId(Long shoppingCartId, Connection connection)
            throws SQLException {
        String selectProductIdQuery = "SELECT products.* FROM carts_products "
                + "JOIN products USING (product_id) WHERE cart_id = ? AND available = ?;";
        PreparedStatement statement = connection.prepareStatement(selectProductIdQuery);
        statement.setLong(1, shoppingCartId);
        statement.setBoolean(2, true);
        ResultSet resultSet = statement.executeQuery();
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Long id = resultSet.getLong("product_id");
            String name = resultSet.getString("name");
            BigDecimal price = resultSet.getBigDecimal("price");
            Product product = new Product(name, price, true);
            product.setId(id);
            products.add(product);
        }
        return products;
    }

    private void deleteShoppingCartFromCartsProducts(Long shoppingCartId, Connection connection)
            throws SQLException {
        String deleteShoppingCartQuery = "DELETE FROM carts_products WHERE cart_id = ?;";
        PreparedStatement statement = connection.prepareStatement(deleteShoppingCartQuery);
        statement.setLong(1, shoppingCartId);
        statement.executeUpdate();
    }
}
