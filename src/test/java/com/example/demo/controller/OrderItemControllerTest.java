package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.OrderItem;
import com.example.demo.service.OrderItemService;

@SpringBootTest
class OrderItemControllerTest {

	@InjectMocks
    private OrderItemController orderItemController; 

    @Mock
    private OrderItemService orderItemService;

    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setQuantity(2);
        orderItem.setPrice(20.0);
    }

    @Test
    public void testCreateOrderItem() {
        // Mock the service method to return the created order item
        when(orderItemService.createOrderItem(any(OrderItem.class))).thenReturn(orderItem);

        // Call the controller method
        OrderItem result = orderItemController.createOrderItem(orderItem);

        // Assert the result
        assertNotNull(result);
        assertEquals(2, result.getQuantity());
        assertEquals(20.0, result.getPrice());

        // Verify that the service method was called
        verify(orderItemService, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    public void testGetOrderItemById() {
        // Mock the service method to return the order item
        when(orderItemService.getOrderItemById(1L)).thenReturn(orderItem);

        // Call the controller method
        OrderItem result = orderItemController.getOrderItemById(1L);

        // Assert the result
        assertNotNull(result);
//        assertEquals("Test Product", result.getProductName());
        assertEquals(2, result.getQuantity());
        assertEquals(20.0, result.getPrice());

        // Verify that the service method was called
        verify(orderItemService, times(1)).getOrderItemById(1L);
    }

    @Test
    public void testGetAllOrderItems() {
        // Mock the service method to return a list of order items
        when(orderItemService.getAllOrderItems()).thenReturn(List.of(orderItem));

        // Call the controller method
        List<OrderItem> result = orderItemController.getAllOrderItems();

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
//        assertEquals("Test Product", result.get(0).getProductName());

        // Verify that the service method was called
        verify(orderItemService, times(1)).getAllOrderItems();
    }

}
