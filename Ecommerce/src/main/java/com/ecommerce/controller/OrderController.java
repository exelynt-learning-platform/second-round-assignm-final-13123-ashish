package com.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entity.Order;
import com.ecommerce.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping
    public Order create(@RequestParam String address) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return service.createOrder(username, address);
    }

    @GetMapping
    public List<Order> getOrders() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        return service.getUserOrders(username);
    }
}