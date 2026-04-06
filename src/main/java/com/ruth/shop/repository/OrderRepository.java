package com.ruth.shop.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruth.shop.entity.Order;
import com.ruth.shop.entity.User;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByUser(User user);
    List<Order> findByStatus(String status);
    Page<Order> findByStatus(String status, Pageable pageable);
    
}
