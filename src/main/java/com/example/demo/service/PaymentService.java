package com.example.demo.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    // Create order on Razorpay
    public String createOrder(double amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient(keyId, keySecret);
        Map<String, Object> orderRequest = Map.of(
                "amount", (int) (amount * 100),
                "currency", "INR",
                "receipt", "txn_123456"
        );

        // Convert Map to JSONObject
        JSONObject orderRequestJson = new JSONObject(orderRequest);

        // Create an order using Razorpay's API
        Order order = razorpay.orders.create(orderRequestJson);

        return order.get("id").toString();
    }
}
