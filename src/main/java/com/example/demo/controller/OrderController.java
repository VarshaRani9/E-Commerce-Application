package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Orders createOrder(@RequestBody Map<String, Object> orderRequest) {
        Long userId = ((Number) orderRequest.get("userId")).longValue();
        List<Long> orderItemIds = (List<Long>) orderRequest.get("orderItemIds");

        logger.info("Received request to create order for userId: {}, with orderItemIds: {}", userId, orderItemIds);
        try {
            Orders order = orderService.createOrder(userId, orderItemIds);
            logger.info("Successfully created order with ID: {}", order.getId());
            return order;
        } catch (Exception e) {
            logger.error("Error creating order for userId: {}, with orderItemIds: {}. Error: {}", userId, orderItemIds, e.getMessage());
            throw e; 
        }
    }

    @GetMapping("/{id}")
    public Orders getOrderById(@PathVariable Long id) {
        logger.info("Received request to fetch order with ID: {}", id);
        try {
            Orders order = orderService.getOrderById(id);
            logger.info("Successfully fetched order with ID: {}", id);
            return order;
        } catch (Exception e) {
            logger.error("Error fetching order with ID: {}. Error: {}", id, e.getMessage());
            throw e; 
        }
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        logger.info("Received request to fetch all orders.");
        List<Orders> orders = orderService.getAllOrders();
        logger.info("Fetched {} orders.", orders.size());
        return orders;
    }
}
