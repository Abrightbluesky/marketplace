package com.ruth.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ruth.shop.entity.Order;
import com.ruth.shop.entity.User;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUser(User user);
    
}
