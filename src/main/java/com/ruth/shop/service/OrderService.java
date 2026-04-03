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
    private final OrderItemRepository orderItemRepository; // 🔥 WAJIB

    // checkout
    public String checkout(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cart> carts = cartRepository.findByUser(user);

        if(carts.isEmpty()){
            throw new RuntimeException("Cart kosong");
        }

        // 🔥 hitung total
        double total = carts.stream()
                .mapToDouble(c -> c.getProduct().getPrice() * c.getQuantity())
                .sum();

        // 🔥 save order dulu
        Order order = Order.builder()
                .user(user)
                .totalPrice(total)
                .build();

        orderRepository.save(order);

        // 🔥 convert Cart → OrderItem (INI YANG KAMU SALAH TADI)
        List<OrderItem> items = carts.stream()
                .map(c -> OrderItem.builder()
                        .productName(c.getProduct().getName())
                        .price(c.getProduct().getPrice())
                        .quantity(c.getQuantity())
                        .order(order)
                        .build()
                ).toList();

        orderItemRepository.saveAll(items);

        // 🔥 set ke order
        order.setItems(items);

        // 🔥 hapus cart
        cartRepository.deleteAll(carts);

        return "Checkout success";
    }

    // get orders
    public List<Order> getOrders(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }
}