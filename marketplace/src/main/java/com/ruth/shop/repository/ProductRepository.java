package com.ruth.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruth.shop.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
