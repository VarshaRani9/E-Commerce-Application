package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@SpringBootTest
class ProductControllerTest {

	@InjectMocks
    private ProductController productController; // The controller to test

    @Mock
    private ProductService productService; // Mocking the ProductService

    private Product product;

    @BeforeEach
    public void setUp() {
        // Setup mock data before each test
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(19.99);
        product.setStockQuantity(100);
    }

    @Test
    public void testGetAllProducts() {
        // Mock the service method to return a list of products
        when(productService.getAllProducts()).thenReturn(List.of(product));

        // Call the controller method
        List<Product> products = productController.getAllProducts();

        // Assert the result
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());

        // Verify that the service method was called
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void testGetProductById() {
        // Mock the service method to return the product
        when(productService.getProductById(1L)).thenReturn(product);

        // Call the controller method
        Product result = productController.getProductById(1L);

        // Assert the result
        assertNotNull(result);
        assertEquals("Test Product", result.getName());

        // Verify that the service method was called
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    public void testCreateProduct() {
        // Mock the service method to return the created product
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        // Call the controller method
        Product result = productController.createProduct(product);

        // Assert the result
        assertNotNull(result);
        assertEquals("Test Product", result.getName());

        // Verify that the service method was called
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        // Mock the service method to return the updated product
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        // Call the controller method
        Product updatedProduct = productController.updateProduct(1L, product);

        // Assert the result
        assertNotNull(updatedProduct);
        assertEquals("Test Product", updatedProduct.getName());

        // Verify that the service method was called
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    public void testDeleteProduct() {
        // Perform the delete operation (no return value)
        doNothing().when(productService).deleteProduct(1L);

        // Call the controller method
        productController.deleteProduct(1L);

        // Verify that the service method was called
        verify(productService, times(1)).deleteProduct(1L);
    }
}
