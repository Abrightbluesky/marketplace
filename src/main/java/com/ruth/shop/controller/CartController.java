package com.ruth.shop.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.ruth.shop.entity.Cart;
import com.ruth.shop.service.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 🔥 ADD
    @PostMapping("/add/{productId}")
    public String add(@PathVariable Long productId ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return cartService.addToCart(productId, email);
    }

    // 🔍 GET CART
    @GetMapping
    public List<Cart> getCart(Principal principal){
        return cartService.getCart(principal.getName());
    }

    // ❌ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        return cartService.deleteCart(id);
    }
}