package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
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
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = LogManager.getLogger(ProductDaoJdbcImpl.class);

    @Override
    public Product create(Product element) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String insertProductQuery = "INSERT INTO products (name, price, available) "
                    + "VALUES (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(insertProductQuery,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setBigDecimal(2, element.getPrice());
            statement.setBoolean(3, element.isAvailable());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
            LOGGER.info(element + " was created.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create " + element, e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectProductQuery = "SELECT * FROM products WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(selectProductQuery);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                return Optional.of(product);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get product with ID " + id, e);
        }
    }

    @Override
    public List<Product> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String selectAllProductsQuery = "SELECT * FROM products WHERE available = ?;";
            PreparedStatement statement = connection.prepareStatement(selectAllProductsQuery);
            statement.setBoolean(1, true);
            ResultSet resultSet = statement.executeQuery();
            List<Product> allProducts = new ArrayList<>();
            while (resultSet.next()) {
                Product product = getProductFromResultSet(resultSet);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all products", e);
        }
    }

    @Override
    public Product update(Product element) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateProductQuery = "UPDATE products SET name = ?, price = ?, available = ? "
                    + "WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(updateProductQuery);
            statement.setString(1, element.getName());
            statement.setBigDecimal(2, element.getPrice());
            statement.setBoolean(3, element.isAvailable());
            statement.setLong(4, element.getId());
            statement.executeUpdate();
            LOGGER.info(element + " was updated.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update " + element, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String updateAvailabilityQuery = "UPDATE products SET available = ? WHERE product_id = ?;";
            PreparedStatement statement = connection.prepareStatement(updateAvailabilityQuery);
            statement.setBoolean(1, false);
            statement.setLong(2, id);
            int numberOfRowsAffected = statement.executeUpdate();
            LOGGER.info("A product with id " + id + " was marked as unavailable.");
            deleteProductFromCarts(id, connection);
            return numberOfRowsAffected != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to mark product with ID "
                    + id + " as unavailable", e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("product_id");
        String name = resultSet.getString("name");
        BigDecimal price = resultSet.getBigDecimal("price");
        boolean available = resultSet.getBoolean("available");
        Product product = new Product(name, price, available);
        product.setId(id);
        return product;
    }

    private void deleteProductFromCarts(Long productId, Connection connection)
            throws SQLException {
        String deleteProductQuery = "DELETE FROM carts_products WHERE product_id = ?;";
        PreparedStatement statement = connection.prepareStatement(deleteProductQuery);
        statement.setLong(1, productId);
        statement.executeUpdate();
        LOGGER.info("A product with id " + productId
                + " was deleted from all shopping carts.");
    }
}
