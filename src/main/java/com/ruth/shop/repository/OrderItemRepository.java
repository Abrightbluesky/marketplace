package com.ruth.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruth.shop.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository
<OrderItem, Long>
{
    
}

