package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        logger.info("Fetching all products");
        try {
            List<Product> products = productRepository.findAll();
            logger.info("Successfully fetched {} products", products.size());
            return products;
        } catch (Exception e) {
            logger.error("Error fetching all products: {}", e.getMessage());
            throw new RuntimeException("Error fetching products", e);  
        }
    }

    public Product getProductById(Long id) {
        logger.info("Fetching product by id: {}", id);
        try {
            return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Product with id {} not found", id);
                    return new RuntimeException("Product not found");
                });
        } catch (Exception e) {
            logger.error("Error fetching product with id {}: {}", id, e.getMessage());
            throw e;  
        }
    }

    public Product createProduct(Product product) {
        logger.info("Creating new product with name: {}", product.getName());
        try {
            Product savedProduct = productRepository.save(product);
            logger.info("Successfully created product with id: {}", savedProduct.getId());
            return savedProduct;
        } catch (Exception e) {
            logger.error("Error creating product: {}", e.getMessage());
            throw new RuntimeException("Error creating product", e);  
        }
    }

    public Product updateProduct(Long id, Product productDetails) {
        logger.info("Updating product with id: {}", id);
        try {
            Product product = getProductById(id);  // Fetch existing product
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setStockQuantity(productDetails.getStockQuantity());

            Product updatedProduct = productRepository.save(product);
            logger.info("Successfully updated product with id: {}", updatedProduct.getId());
            return updatedProduct;
        } catch (Exception e) {
            logger.error("Error updating product with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error updating product", e);  
        }
    }

    public void deleteProduct(Long id) {
        logger.info("Deleting product with id: {}", id);
        try {
            productRepository.deleteById(id);
            logger.info("Successfully deleted product with id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting product with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting product", e);  // Throw exception after logging
        }
    }
}
