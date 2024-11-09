package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Orders;
import com.example.demo.service.OrderService;

@SpringBootTest
class OrderControllerTest {

	@InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private Orders order;

    @BeforeEach
    public void setUp() {
        // Setup Order object before each test
        order = new Orders();
        order.setId(1L);
        order.setTotalAmount(100.0);
    }

    @Test
    public void testCreateOrder() {
        // Mock the service method to return an order
        Long userId = 1L;
        List<Long> orderItemIds = Arrays.asList(1L, 2L);

        // Mock the createOrder method of OrderService
        when(orderService.createOrder(userId, orderItemIds)).thenReturn(order);

        // Prepare input map
        Map<String, Object> orderRequest = Map.of(
            "userId", userId,
            "orderItemIds", orderItemIds
        );

        // Call the controller method
        Orders result = orderController.createOrder(orderRequest);

        // Assert the result
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getTotalAmount());

        // Verify the interaction with the service
        verify(orderService, times(1)).createOrder(userId, orderItemIds);
    }

    @Test
    public void testGetOrderById() {
        // Mock the service method to return an order by id
        when(orderService.getOrderById(1L)).thenReturn(order);

        // Call the controller method
        Orders result = orderController.getOrderById(1L);

        // Assert the result
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getTotalAmount());

        // Verify the interaction with the service
//        verify(orderService, times(1)).getOrderById(1L);
    }

    @Test
    public void testGetAllOrders() {
        // Mock the service method to return a list of orders
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order));

        // Call the controller method
        List<Orders> result = orderController.getAllOrders();

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getTotalAmount());

        // Verify the interaction with the service
        verify(orderService, times(1)).getAllOrders();
    }

}
