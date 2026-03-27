package com.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.entity.Cart;
import com.ecommerce.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam Long productId,
            @RequestParam int qty,
            Principal principal) {

        String username = principal.getName();
        Cart cart = cartService.addToCart(username, productId, qty);

        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Principal principal) {

        String username = principal.getName();
        Cart cart = cartService.getCartByUser(username);

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Long itemId,
            Principal principal) {

        String username = principal.getName();
        cartService.removeItem(username, itemId);

        return ResponseEntity.ok("Item removed successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateQuantity(
            @RequestParam Long itemId,
            @RequestParam int qty,
            Principal principal) {

        String username = principal.getName();
        Cart cart = cartService.updateQuantity(username, itemId, qty);

        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Principal principal) {

        String username = principal.getName();
        cartService.clearCart(username);

        return ResponseEntity.ok("Cart cleared");
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotal(Principal principal) {

        String username = principal.getName();
        double total = cartService.calculateTotal(username);

        return ResponseEntity.ok(total);
    }
}