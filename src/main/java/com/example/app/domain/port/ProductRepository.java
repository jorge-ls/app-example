package com.example.app.domain.port;

import com.example.app.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    Product update(Product product);
    void deleteById(Long id);
    boolean existsById(Long id);
}
