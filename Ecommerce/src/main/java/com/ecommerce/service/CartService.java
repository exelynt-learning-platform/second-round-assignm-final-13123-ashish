package com.ecommerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.entity.Cart;
import com.ecommerce.entity.CartItem;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    public Cart addToCart(String username, Long productId, int qty) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setItems(new ArrayList<>()); 
                    return newCart;
                });

        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + qty);
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(qty);
            item.setCart(cart);

            cart.getItems().add(item);
        }

        return cartRepo.save(cart);
    }

    public Cart getCartByUser(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void removeItem(String username, Long itemId) {

        Cart cart = getCartByUser(username);

        cart.getItems().removeIf(item -> item.getId().equals(itemId));

        cartRepo.save(cart);
    }

    public Cart updateQuantity(String username, Long itemId, int qty) {

        Cart cart = getCartByUser(username);

        cart.getItems().forEach(item -> {
            if (item.getId().equals(itemId)) {
                item.setQuantity(qty);
            }
        });

        return cartRepo.save(cart);
    }

    public void clearCart(String username) {

        Cart cart = getCartByUser(username);

        cart.getItems().clear();

        cartRepo.save(cart);
    }

    public double calculateTotal(String username) {

        Cart cart = getCartByUser(username);

        return cart.getItems().stream()
                .mapToDouble(item ->
                        item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}