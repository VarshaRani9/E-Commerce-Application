package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@SpringBootTest
class ProductServiceTest {

	@InjectMocks
    private ProductService productService;  // Service that we want to test

    @Mock
    private ProductRepository productRepository;  // Mocked repository

    private Product product;

    @BeforeEach
    public void setup() {
        // Initializing a sample product to be used in the tests
        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(100.0);
        product.setStockQuantity(50);
    }

    @Test
    public void testGetAllProducts() {
        // Given
        when(productRepository.findAll()).thenReturn(Arrays.asList(product));

        // When
        List<Product> productList = productService.getAllProducts();

        // Then
        assertNotNull(productList);
        assertEquals(1, productList.size());
        assertEquals("Sample Product", productList.get(0).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById_Success() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        // When
        Product fetchedProduct = productService.getProductById(1L);

        // Then
        assertNotNull(fetchedProduct);
        assertEquals("Sample Product", fetchedProduct.getName());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetProductById_NotFound() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            productService.getProductById(1L);
        });
    }

    @Test
    public void testCreateProduct() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        Product createdProduct = productService.createProduct(product);

        // Then
        assertNotNull(createdProduct);
        assertEquals("Sample Product", createdProduct.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        // Given
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        product.setName("Updated Product");
        Product updatedProduct = productService.updateProduct(1L, product);

        // Then
        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        verify(productRepository, times(1)).findById(anyLong());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        // Given
        doNothing().when(productRepository).deleteById(anyLong());

        // When
        productService.deleteProduct(1L);

        // Then
        verify(productRepository, times(1)).deleteById(anyLong());
    }

}
