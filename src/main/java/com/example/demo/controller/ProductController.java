package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        logger.info("Fetching all products.");
        List<Product> products = productService.getAllProducts();
        logger.info("Fetched {} products.", products.size());
        return products;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        logger.info("Fetching product with ID: {}", id);
        try {
            Product product = productService.getProductById(id);
            logger.info("Product with ID: {} found.", id);
            return product;
        } catch (Exception e) {
            logger.error("Error fetching product with ID: {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        logger.info("Creating new product: {}", product.getName());
        try {
            Product createdProduct = productService.createProduct(product);
            logger.info("Created product with ID: {}", createdProduct.getId());
            return createdProduct;
        } catch (Exception e) {
            logger.error("Error creating product: {}. Error: {}", product.getName(), e.getMessage());
            throw e; 
        }
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        logger.info("Updating product with ID: {}", id);
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            logger.info("Updated product with ID: {}", id);
            return updatedProduct;
        } catch (Exception e) {
            logger.error("Error updating product with ID: {}. Error: {}", id, e.getMessage());
            throw e; 
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        logger.info("Deleting product with ID: {}", id);
        try {
            productService.deleteProduct(id);
            logger.info("Deleted product with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting product with ID: {}. Error: {}", id, e.getMessage());
            throw e; 
        }
    }
}
