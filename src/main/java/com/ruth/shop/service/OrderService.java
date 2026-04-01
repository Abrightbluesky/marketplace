package com.ruth.shop.service;


import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.ruth.shop.entity.*;
import com.ruth.shop.repository.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    // checkout

    public String checkout(String email){
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> carts = cartRepository.findByUser(user);

        double total = carts.stream()
                        .mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity())
                        .sum();

        Order order = Order.builder()
        .user(user)
        .items(carts)
        .totalPrice(total)
        .build();
        
        orderRepository.save(order);

        // kosongkan cart

        cartRepository.deleteAll(carts);


        return "Checkout sukses";

    }

    // get orders

    public List<Order> getOrders(String email){
        User user = userRepository.findByEmail(email).orElseThrow();
        return orderRepository.findByUser(user);
    }
    
}
