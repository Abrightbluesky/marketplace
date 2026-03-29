package com.ruth.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ruth.shop.entity.*;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
}
