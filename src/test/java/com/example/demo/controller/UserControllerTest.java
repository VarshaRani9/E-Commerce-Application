package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@SpringBootTest
class UserControllerTest {

	@InjectMocks
    private UserController userController; 

    @Mock
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    public void testCreateUser() {
        // Mock the service method to return the user object
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Call the controller method
        User result = userController.createUser(user);

        // Assert the result
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());

        // Verify that the service method was called
        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void testGetUserById() {
        // Mock the service method to return the user object
        when(userService.getUserById(1L)).thenReturn(user);

        // Call the controller method
        User result = userController.getUserById(1L);

        // Assert the result
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john.doe@example.com", result.getEmail());

        // Verify that the service method was called
//        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    public void testGetAllUsers() {
        // Mock the service method to return a list of users
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user));

        // Call the controller method
        List<User> result = userController.getAllUsers();

        // Assert the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());

        // Verify that the service method was called
//        verify(userService, times(1)).getAllUsers();
    }

} 
