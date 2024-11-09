package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.example.demo.entity.OrderItem;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;

@SpringBootTest
class OrderServiceTest {

	@InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;  // Mocked repository for orders

    @Mock
    private OrderItemRepository orderItemRepository;  // Mocked repository for order items

    @Mock
    private UserRepository userRepository;  // Mocked repository for users

    private User user;
    private OrderItem orderItem;
    private Orders order;

    @BeforeEach
    public void setup() {
        // Initialize User and OrderItem for the tests
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setPrice(100.0);
        orderItem.setQuantity(2);
        orderItem.setProduct(new Product("Product 1", 100.0, 2));  // Assuming a Product entity

        order = new Orders(user, Arrays.asList(orderItem), 200.0);  // Total amount = 2 * 100.0
    }

    @Test
    public void testCreateOrder() {
        // Given
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderItemRepository.findAllById(any())).thenReturn(Arrays.asList(orderItem));
        when(orderRepository.save(any(Orders.class))).thenReturn(order);

        // When
        Orders createdOrder = orderService.createOrder(1L, Arrays.asList(1L));

        // Then
        assertNotNull(createdOrder);
        assertEquals(200.0, createdOrder.getTotalAmount());
        assertEquals("John Doe", createdOrder.getUser().getName());
        verify(orderRepository, times(1)).save(any(Orders.class));  
    }

    @Test
    public void testCreateOrder_UserNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(1L, Arrays.asList(1L));
        });

        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
    public void testCreateOrder_NoOrderItemsFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(orderItemRepository.findAllById(any())).thenReturn(Arrays.asList()); 

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(1L, Arrays.asList(1L));
        });

        assertEquals("No valid order items found for the given IDs", exception.getMessage());
    }

    @Test
    public void testGetOrderById_Success() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Orders fetchedOrder = orderService.getOrderById(1L);

        assertNotNull(fetchedOrder);
        assertEquals(200.0, fetchedOrder.getTotalAmount());
        assertEquals("John Doe", fetchedOrder.getUser().getName());
        verify(orderRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetOrderById_NotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });
    }

    @Test
    public void testGetAllOrders() {

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<Orders> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(200.0, orders.get(0).getTotalAmount());
        verify(orderRepository, times(1)).findAll();
    }
}
