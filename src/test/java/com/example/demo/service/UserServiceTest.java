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

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@InjectMocks
    private UserService userService;  // Service for testing

    @Mock
    private UserRepository userRepository;  // Mocked repository for users

    private User user;

    @BeforeEach
    public void setup() {
        // Initialize User for the tests
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@example.com");
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
        assertEquals("johndoe@example.com", createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));  // Ensure save method is called once
    }

    @Test
    public void testGetUserById_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User fetchedUser = userService.getUserById(1L);

        assertNotNull(fetchedUser);
        assertEquals(1L, fetchedUser.getId());
        assertEquals("John Doe", fetchedUser.getName());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetUserById_NotFound() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(1L);
        });
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));

        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("John Doe", users.get(0).getName());
        verify(userRepository, times(1)).findAll();
    }

}
