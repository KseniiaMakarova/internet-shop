package com.internet.shop.dao.jdbc;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.exception.DataProcessingException;
import com.internet.shop.lib.Dao;
import com.internet.shop.model.Product;
import com.internet.shop.util.ConnectionUtil;
import com.internet.shop.web.filter.AuthenticationFilter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);

    @Override
    public Product create(Product element) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, element.getName());
            statement.setBigDecimal(2, element.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
            LOGGER.info(element + " was created.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to create product", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        String query = "SELECT * FROM products WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            } else {
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                Product product = new Product(name, price);
                product.setId(id);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to get product", e);
        }
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM products;";
        List<Product> allProducts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                BigDecimal price = resultSet.getBigDecimal("price");
                Product product = new Product(name, price);
                product.setId(id);
                allProducts.add(product);
            }
            return allProducts;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to retrieve all products", e);
        }
    }

    @Override
    public Product update(Product element) {
        String query = "UPDATE products SET name = ?, price = ? "
                + "WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, element.getName());
            statement.setBigDecimal(2, element.getPrice());
            statement.setLong(3, element.getId());
            statement.executeUpdate();
            LOGGER.info(element + " was updated.");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to update product", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE product_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            int result = statement.executeUpdate();
            LOGGER.info("A product with id " + id + " was deleted.");
            return result != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Unable to delete product", e);
        }
    }
}
