package com.internet.shop.dao.impl;

import com.internet.shop.dao.ProductDao;
import com.internet.shop.db.Storage;
import com.internet.shop.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addToList(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(ind -> Storage.products.get(ind).getId().equals(product.getId()))
                .forEach(ind -> Storage.products.set(ind, product));
        return product;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.products.removeIf(product -> product.getId().equals(id));
    }
}
