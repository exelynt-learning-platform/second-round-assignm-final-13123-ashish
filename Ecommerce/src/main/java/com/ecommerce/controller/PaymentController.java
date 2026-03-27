package com.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.service.PaymentService;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService service;

    @PostMapping("/{orderId}")
    public String pay(@PathVariable Long orderId) throws Exception {
        return service.makePayment(orderId);
    }
}
