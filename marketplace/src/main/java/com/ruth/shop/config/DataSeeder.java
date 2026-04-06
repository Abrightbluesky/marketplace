package com.ruth.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ruth.shop.entity.Product;
import com.ruth.shop.entity.Role;
import com.ruth.shop.entity.User;
import com.ruth.shop.repository.ProductRepository;
import com.ruth.shop.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        // 🔐 ADMIN (cek berdasarkan email)
        if(userRepository.findByEmail("admin@mail.com").isEmpty()){
            userRepository.save(User.builder()
                    .name("Admin")
                    .email("admin@mail.com")
                    .password(passwordEncoder.encode("123456"))
                    .role(Role.ADMIN)
                    .build());
        }

        // 👤 USERS (cek satu-satu)
        for(int i = 1; i <= 10; i++){
            String email = "user" + i + "@mail.com";

            if(userRepository.findByEmail(email).isEmpty()){
                userRepository.save(User.builder()
                        .name("User " + i)
                        .email(email)
                        .password(passwordEncoder.encode("123456"))
                        .role(Role.USER)
                        .build());
            }
        }

        // 📦 PRODUCTS
        if(productRepository.count() == 0){
            for(int i = 1; i <= 100; i++){
                productRepository.save(Product.builder()
                        .name("Product " + i)
                        .description("Description product " + i)
                        .price(10000.0 + (i * 500))
                        .stock(10 + i)
                        .build());
            }
        }

        System.out.println("🔥 DATA SEEDED SUCCESSFULLY");
    }
}