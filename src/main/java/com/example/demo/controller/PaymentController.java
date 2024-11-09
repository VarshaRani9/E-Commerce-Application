package com.example.demo.controller;

import com.example.demo.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Endpoint to create an order in Razorpay
    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrder(@RequestParam double amount) {
        try {
            // Call service to create an order and return order_id
            String orderId = paymentService.createOrder(amount);
            return ResponseEntity.ok(orderId); 
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body("Error creating order: " + e.getMessage());
        }
    }
}
