package com.example.app.infrastructure.persistence;

import com.example.app.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryImplTest {

    @Mock
    private SpringDataProductRepository springDataProductRepository;

    @InjectMocks
    private ProductRepositoryImpl productRepository;

    private ProductEntity testEntity;
    private Product testProduct;

    @BeforeEach
    void setUp() {
        testEntity = entity(1L, "Test Product", "Test Description", new BigDecimal("19.99"), 10);
        testProduct = new Product(1L, "Test Product", "Test Description", new BigDecimal("19.99"), 10);
    }

    @Test
    void save_ShouldReturnSavedProduct() {
        when(springDataProductRepository.save(any(ProductEntity.class))).thenReturn(testEntity);

        Product result = productRepository.save(testProduct);

        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        assertEquals(testProduct.getName(), result.getName());
        verify(springDataProductRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void findById_WhenProductExists_ShouldReturnProduct() {
        when(springDataProductRepository.findById(1L)).thenReturn(Optional.of(testEntity));

        Optional<Product> result = productRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(testProduct.getId(), result.get().getId());
        assertEquals(testProduct.getName(), result.get().getName());
        verify(springDataProductRepository, times(1)).findById(1L);
    }

    @Test
    void findById_WhenProductNotExists_ShouldReturnEmpty() {
        when(springDataProductRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Product> result = productRepository.findById(999L);

        assertFalse(result.isPresent());
        verify(springDataProductRepository, times(1)).findById(999L);
    }

    @Test
    void findAll_ShouldReturnAllProducts() {
        List<ProductEntity> entities = Arrays.asList(
                testEntity,
                entity(2L, "Product 2", "Description 2", new BigDecimal("29.99"), 5)
        );
        when(springDataProductRepository.findAll()).thenReturn(entities);

        List<Product> result = productRepository.findAll();

        assertEquals(2, result.size());
        assertEquals(entities.get(0).getId(), result.get(0).getId());
        assertEquals(entities.get(1).getId(), result.get(1).getId());
        verify(springDataProductRepository, times(1)).findAll();
    }

    @Test
    void update_ShouldReturnUpdatedProduct() {
        when(springDataProductRepository.save(any(ProductEntity.class))).thenReturn(testEntity);

        Product result = productRepository.update(testProduct);

        assertNotNull(result);
        assertEquals(testProduct.getId(), result.getId());
        verify(springDataProductRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void deleteById_ShouldCallRepositoryDelete() {
        doNothing().when(springDataProductRepository).deleteById(1L);

        productRepository.deleteById(1L);

        verify(springDataProductRepository, times(1)).deleteById(1L);
    }

    @Test
    void existsById_WhenProductExists_ShouldReturnTrue() {
        when(springDataProductRepository.existsById(1L)).thenReturn(true);

        boolean result = productRepository.existsById(1L);

        assertTrue(result);
        verify(springDataProductRepository, times(1)).existsById(1L);
    }

    @Test
    void existsById_WhenProductNotExists_ShouldReturnFalse() {
        when(springDataProductRepository.existsById(999L)).thenReturn(false);

        boolean result = productRepository.existsById(999L);

        assertFalse(result);
        verify(springDataProductRepository, times(1)).existsById(999L);
    }

    private static ProductEntity entity(Long id, String name, String description, BigDecimal price, Integer stock) {
        ProductEntity entity = new ProductEntity(name, description, price, stock);
        entity.setId(id);
        return entity;
    }
}
