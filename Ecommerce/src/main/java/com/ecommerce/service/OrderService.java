package com.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.*;
import com.ecommerce.repository.*;

@Service
public class OrderService {

    @Autowired private CartRepository cartRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private OrderRepository orderRepo;

    public Order createOrder(String username, String address) {

        User user = userRepo.findByUsername(username).orElseThrow();
        Cart cart = cartRepo.findByUser(user).orElseThrow();

        Order order = new Order();
        order.setUser(user);
        order.setStatus("CREATED");
        order.setShippingAddress(address);

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (CartItem ci : cart.getItems()) {

            OrderItem item = new OrderItem();
            item.setProductName(ci.getProduct().getName());
            item.setPrice(ci.getProduct().getPrice());
            item.setQuantity(ci.getQuantity());

            total += ci.getProduct().getPrice() * ci.getQuantity();

            orderItems.add(item);
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        cart.getItems().clear();
        cartRepo.save(cart);

        return orderRepo.save(order);
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        return orderRepo.findByUser(user);
    }
}