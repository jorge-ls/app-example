package com.example.app.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void constructorWithId_ShouldCreateProductWithAllFields() {
        Product product = new Product(1L, "Test Product", "Test Description", new BigDecimal("19.99"), 10);

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("19.99"), product.getPrice());
        assertEquals(10, product.getStock());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
    }

    @Test
    void constructorWithoutId_ShouldCreateProductWithoutId() {
        Product product = new Product("Test Product", "Test Description", new BigDecimal("19.99"), 10);

        assertNull(product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("19.99"), product.getPrice());
        assertEquals(10, product.getStock());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        Product product = new Product();
        LocalDateTime now = LocalDateTime.now();

        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(new BigDecimal("19.99"));
        product.setStock(10);
        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals("Test Description", product.getDescription());
        assertEquals(new BigDecimal("19.99"), product.getPrice());
        assertEquals(10, product.getStock());
        assertEquals(now, product.getCreatedAt());
        assertEquals(now, product.getUpdatedAt());
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        Product product1 = new Product(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 5);
        Product product2 = new Product(1L, "Product 2", "Description 2", new BigDecimal("20.00"), 10);

        assertEquals(product1, product2);
    }

    @Test
    void equals_WithDifferentId_ShouldReturnFalse() {
        Product product1 = new Product(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 5);
        Product product2 = new Product(2L, "Product 2", "Description 2", new BigDecimal("20.00"), 10);

        assertNotEquals(product1, product2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        Product product = new Product(1L, "Product", "Description", new BigDecimal("10.00"), 5);

        assertNotEquals(product, null);
    }

    @Test
    void hashCode_WithSameId_ShouldReturnSameHashCode() {
        Product product1 = new Product(1L, "Product 1", "Description 1", new BigDecimal("10.00"), 5);
        Product product2 = new Product(1L, "Product 2", "Description 2", new BigDecimal("20.00"), 10);

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void toString_ShouldReturnProductStringRepresentation() {
        Product product = new Product(1L, "Test Product", "Test Description", new BigDecimal("19.99"), 10);

        String result = product.toString();

        assertTrue(result.contains("Test Product"));
        assertTrue(result.contains("Test Description"));
        assertTrue(result.contains("19.99"));
        assertTrue(result.contains("10"));
    }
}
