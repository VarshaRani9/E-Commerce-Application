package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    PaymentOrder findByOrderId(String orderId);
}
