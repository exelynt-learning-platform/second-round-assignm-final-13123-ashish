package com.ecommerce.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Order;
import com.ecommerce.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {

    @Autowired
    private OrderRepository orderRepo;
    

    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_your_key";
    }

    public String makePayment(Long orderId) throws Exception {

        Order order = orderRepo.findById(orderId).orElseThrow();

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int)(order.getTotalPrice() * 100)); 
        params.put("currency", "inr");
        params.put("payment_method_types", List.of("card"));

        PaymentIntent intent = PaymentIntent.create(params);

    
        order.setStatus("PAID");

        orderRepo.save(order);

        return intent.getClientSecret();
    }
}
