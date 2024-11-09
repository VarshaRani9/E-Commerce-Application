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
import com.example.demo.entity.Product;
import com.example.demo.repository.OrderItemRepository;
import com.example.demo.repository.ProductRepository;

@SpringBootTest
class OrderItemServiceTest {

	@InjectMocks
    private OrderItemService orderItemService;  // Service for testing

    @Mock
    private OrderItemRepository orderItemRepository;  // Mocked repository for order items

    @Mock
    private ProductRepository productRepository;  // Mocked repository for products

    private OrderItem orderItem;
    private Product product;

    @BeforeEach
    public void setup() {
        // Initialize Product and OrderItem for the tests
        product = new Product();
        product.setId(1L);
        product.setName("Sample Product");
        product.setPrice(100.0);
        product.setStockQuantity(50);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItem.setPrice(product.getPrice());  
    }

    @Test
    public void testCreateOrderItem() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);

        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);

        assertNotNull(createdOrderItem);
        assertEquals("Sample Product", createdOrderItem.getProduct().getName());
        assertEquals(100.0, createdOrderItem.getPrice());
        verify(productRepository, times(1)).findById(anyLong());
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    public void testCreateOrderItem_ProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderItemService.createOrderItem(orderItem);
        });
    }

    @Test
    public void testGetOrderItemById_Success() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));

        OrderItem fetchedOrderItem = orderItemService.getOrderItemById(1L);

        assertNotNull(fetchedOrderItem);
        assertEquals(1L, fetchedOrderItem.getId());
        assertEquals("Sample Product", fetchedOrderItem.getProduct().getName());
        verify(orderItemRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetOrderItemById_NotFound() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            orderItemService.getOrderItemById(1L);
        });
    }

    @Test
    public void testGetAllOrderItems() {
        when(orderItemRepository.findAll()).thenReturn(Arrays.asList(orderItem));

        List<OrderItem> orderItems = orderItemService.getAllOrderItems();

        assertNotNull(orderItems);
        assertEquals(1, orderItems.size());
        assertEquals("Sample Product", orderItems.get(0).getProduct().getName());
        verify(orderItemRepository, times(1)).findAll();
    }
}
