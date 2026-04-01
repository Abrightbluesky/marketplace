package com.ruth.shop.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruth.shop.entity.Order;
import com.ruth.shop.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // checout
    @PostMapping("/checkout")
    public String checkout(){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        /// debug
        System.out.println("AUTH OBJECT:" + auth);
        String email = auth.getName();
        return orderService.checkout(email);

    }

    // history 

    @GetMapping
    public List<Order> getOrders(Principal principal){
        return orderService.getOrders(principal.getName());
    }
    
}
