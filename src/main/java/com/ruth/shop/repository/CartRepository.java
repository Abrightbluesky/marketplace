package com.ruth.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ruth.shop.entity.Cart;
import com.ruth.shop.entity.User;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);
}   