package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;

@Service
public class OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderItem createOrderItem(OrderItem orderItem) {
        logger.info("Creating new OrderItem with product ID: {}", orderItem.getProduct().getId());

        try {
            // Fetch full Product details by ID
            Product product = productRepository.findById(orderItem.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            // Set the fetched product and price from the product
            orderItem.setProduct(product);
            orderItem.setPrice(product.getPrice());  
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            logger.info("Successfully created OrderItem with ID: {}", savedOrderItem.getId());
            return savedOrderItem;

        } catch (Exception e) {
            logger.error("Error creating OrderItem: {}", e.getMessage());
            throw new RuntimeException("Error creating order item", e);
        }
    }

    public OrderItem getOrderItemById(Long id) {
        logger.info("Fetching OrderItem by ID: {}", id);

        try {
            return orderItemRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("OrderItem with ID {} not found", id);
                        return new RuntimeException("Order Item not found");
                    });
        } catch (Exception e) {
            logger.error("Error fetching OrderItem with ID {}: {}", id, e.getMessage());
            throw e;  
        }
    }

    public List<OrderItem> getAllOrderItems() {
        logger.info("Fetching all OrderItems");

        try {
            List<OrderItem> orderItems = orderItemRepository.findAll();
            logger.info("Successfully fetched {} OrderItems", orderItems.size());
            return orderItems;
        } catch (Exception e) {
            logger.error("Error fetching all OrderItems: {}", e.getMessage());
            throw new RuntimeException("Error fetching order items", e);
        }
    }
}
