package com.ecommerce.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private double totalPrice;
    private String status;
    private String shippingAddress;
}
