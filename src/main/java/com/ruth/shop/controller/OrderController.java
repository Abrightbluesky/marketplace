package com.ruth.shop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ruth.shop.dto.ApiResponse;
import com.ruth.shop.dto.OrderResponse;
import com.ruth.shop.entity.Order;
import com.ruth.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // =========================
    // CHECKOUT
    // =========================
    @PostMapping("/checkout")
    public String checkout(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // ✅ FIX

        return orderService.checkout(email);
    }

    @PostMapping("/pay")
    public String pay(@RequestParam Long orderId){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        return orderService.pay(orderId, email);
    }

    // =========================
    // USER ORDERS (DTO)
    // =========================
    @GetMapping
    public List<OrderResponse> getOrders(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // ✅ FIX

        return orderService.getOrders(email);
    }

    // =========================
    // ADMIN ORDERS (SIMPLE)
    // =========================
    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<?>> getAllOrders(){

        List<Order> orders = orderService.getAllOrders(); // ✅ FIX (tanpa pagination)

        return ResponseEntity.ok(
                ApiResponse.builder()
                        .message("success")
                        .data(orders)
                        .build()
        );
    }
}