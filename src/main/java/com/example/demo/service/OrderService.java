package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Orders;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserRepository userRepository;

    public Orders createOrder(Long userId, List<Long> orderItemIds) {
        logger.info("Creating order for user with ID: {}", userId);

        try {
            // Fetch User
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
            logger.info("User found: {}", user.getId());

            // Fetch Order Items
            List<OrderItem> orderItems = orderItemRepository.findAllById(orderItemIds);
            if (orderItems.isEmpty()) {
                logger.warn("No valid order items found for the given IDs: {}", orderItemIds);
                throw new RuntimeException("No valid order items found for the given IDs");
            }

            // Calculate Total Amount
            double totalAmount = orderItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getQuantity())
                    .sum();
            logger.info("Total order amount calculated: {}", totalAmount);

            // Create and save Order
            Orders order = new Orders(user, orderItems, totalAmount);
            Orders savedOrder = orderRepository.save(order);
            logger.info("Order created successfully with ID: {}", savedOrder.getId());

            return savedOrder;
        } catch (Exception e) {
            logger.error("Error occurred while creating order: {}", e.getMessage(), e);
            throw new RuntimeException("Error creating order", e);
        }
    }

    public Orders getOrderById(Long id) {
        logger.info("Fetching order with ID: {}", id);
        try {
            return orderRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Order with ID {} not found", id);
                        return new RuntimeException("Order not found");
                    });
        } catch (Exception e) {
            logger.error("Error fetching order with ID {}: {}", id, e.getMessage());
            throw e;  
        }
    }

    public List<Orders> getAllOrders() {
        logger.info("Fetching all orders");
        try {
            List<Orders> orders = orderRepository.findAll();
            logger.info("Successfully fetched {} orders", orders.size());
            return orders;
        } catch (Exception e) {
            logger.error("Error fetching all orders: {}", e.getMessage());
            throw new RuntimeException("Error fetching orders", e);
        }
    }
}
