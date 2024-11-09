package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        logger.info("Received request to create User: {}", user);
        try {
            User createdUser = userService.createUser(user);
            logger.info("Successfully created User with ID: {}", createdUser.getId());
            return createdUser;
        } catch (Exception e) {
            logger.error("Error creating User: {}. Error: {}", user, e.getMessage());
            throw e; 
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        logger.info("Received request to fetch User with ID: {}", id);
        try {
            User user = userService.getUserById(id);
            logger.info("Successfully fetched User with ID: {}", id);
            return user;
        } catch (Exception e) {
            logger.error("Error fetching User with ID: {}. Error: {}", id, e.getMessage());
            throw e; 
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info("Received request to fetch all Users.");
        List<User> users = userService.getAllUsers();
        logger.info("Fetched {} Users.", users.size());
        return users;
    }
}
