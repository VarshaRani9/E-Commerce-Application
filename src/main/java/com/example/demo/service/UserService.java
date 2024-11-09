package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        logger.info("Creating new User with ID: {}", user.getId());
        try {
            User savedUser = userRepository.save(user);
            logger.info("Successfully created User with ID: {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error creating User: {}", e.getMessage());
            throw new RuntimeException("Error creating user", e);
        }
    }

    public User getUserById(Long id) {
        logger.info("Fetching User by ID: {}", id);
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("User with ID {} not found", id);
                        return new RuntimeException("User not found");
                    });
        } catch (Exception e) {
            logger.error("Error fetching User with ID {}: {}", id, e.getMessage());
            throw e;  
        }
    }

    public List<User> getAllUsers() {
        logger.info("Fetching all Users");
        try {
            List<User> users = userRepository.findAll();
            logger.info("Successfully fetched {} Users", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error fetching all Users: {}", e.getMessage());
            throw new RuntimeException("Error fetching users", e);
        }
    }
}
