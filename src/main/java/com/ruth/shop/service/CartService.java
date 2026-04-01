package com.ruth.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ruth.shop.entity.Cart;
import com.ruth.shop.entity.Product;
import com.ruth.shop.entity.User;
import com.ruth.shop.repository.CartRepository;
import com.ruth.shop.repository.ProductRepository;
import com.ruth.shop.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 🔥 ADD TO CART
    public String addToCart(Long productId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = Cart.builder()
                .user(user)
                .product(product)
                .quantity(1)
                .build();

        cartRepository.save(cart);

        return "Added to cart";
    }

    // 🔍 GET CART
    public List<Cart> getCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user);
    }

    // ❌ DELETE CART
    public String deleteCart(Long id) {

        cartRepository.deleteById(id);

        return "Deleted";
    }
}