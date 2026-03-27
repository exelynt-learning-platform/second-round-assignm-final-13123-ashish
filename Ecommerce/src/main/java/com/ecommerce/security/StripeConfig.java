package com.ecommerce.security;

import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class StripeConfig {

    @PostConstruct
    public void init() {
        com.stripe.Stripe.apiKey = "sk_test_your_key";
    }
}
