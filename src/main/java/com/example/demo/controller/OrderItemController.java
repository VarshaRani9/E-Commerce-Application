package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.OrderItem;
import com.example.demo.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/orderItems")
public class OrderItemController {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public OrderItem createOrderItem(@RequestBody OrderItem orderItem) {
        logger.info("Received request to create OrderItem: {}", orderItem);
        try {
            OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
            logger.info("Successfully created OrderItem with ID: {}", createdOrderItem.getId());
            return createdOrderItem;
        } catch (Exception e) {
            logger.error("Error creating OrderItem: {}. Error: {}", orderItem, e.getMessage());
            throw e; 
        }
    }

    @GetMapping("/{id}")
    public OrderItem getOrderItemById(@PathVariable Long id) {
        logger.info("Received request to fetch OrderItem with ID: {}", id);
        try {
            OrderItem orderItem = orderItemService.getOrderItemById(id);
            logger.info("Successfully fetched OrderItem with ID: {}", id);
            return orderItem;
        } catch (Exception e) {
            logger.error("Error fetching OrderItem with ID: {}. Error: {}", id, e.getMessage());
            throw e;
        }
    }

    @GetMapping
    public List<OrderItem> getAllOrderItems() {
        logger.info("Received request to fetch all OrderItems.");
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        logger.info("Fetched {} OrderItems.", orderItems.size());
        return orderItems;
    }
}
