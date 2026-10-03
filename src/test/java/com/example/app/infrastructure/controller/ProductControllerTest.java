package com.example.app.infrastructure.controller;

import com.example.app.application.service.ProductService;
import com.example.app.domain.exception.ProductNotFoundException;
import com.example.app.domain.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(1L, "Test Product", "Test Description", new BigDecimal("19.99"), 10);
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        when(productService.createProduct(any(), any(), any(), any())).thenReturn(testProduct);

        ProductController.CreateProductRequest request = new ProductController.CreateProductRequest(
                "Test Product", "Test Description", new BigDecimal("19.99"), 10
        );

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.price").value(19.99))
                .andExpect(jsonPath("$.stock").value(10));
    }

    @Test
    void getProductById_WhenProductExists_ShouldReturnProduct() throws Exception {
        when(productService.getProductById(1L)).thenReturn(testProduct);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Product"));
    }

    @Test
    void getProductById_WhenProductNotExists_ShouldReturnNotFound() throws Exception {
        when(productService.getProductById(999L))
                .thenThrow(new ProductNotFoundException(999L));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product not found with id: 999"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void getAllProducts_ShouldReturnAllProducts() throws Exception {
        List<Product> products = Arrays.asList(
                testProduct,
                new Product(2L, "Product 2", "Description 2", new BigDecimal("29.99"), 5)
        );
        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void updateProduct_WhenProductExists_ShouldReturnUpdatedProduct() throws Exception {
        when(productService.updateProduct(eq(1L), any(), any(), any(), any())).thenReturn(testProduct);

        ProductController.CreateProductRequest request = new ProductController.CreateProductRequest(
                "Updated Product", "Updated Description", new BigDecimal("25.99"), 15
        );

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateProduct_WhenProductNotExists_ShouldReturnNotFound() throws Exception {
        when(productService.updateProduct(eq(999L), any(), any(), any(), any()))
                .thenThrow(new ProductNotFoundException(999L));

        ProductController.CreateProductRequest request = new ProductController.CreateProductRequest(
                "Updated Product", "Updated Description", new BigDecimal("25.99"), 15
        );

        mockMvc.perform(put("/api/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product not found with id: 999"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void deleteProduct_WhenProductExists_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProduct_WhenProductNotExists_ShouldReturnNotFound() throws Exception {
        doThrow(new ProductNotFoundException(999L))
                .when(productService).deleteProduct(999L);

        mockMvc.perform(delete("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Product not found with id: 999"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
