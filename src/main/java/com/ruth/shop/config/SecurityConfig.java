package com.ruth.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ruth.shop.security.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter; // ✅ dipakai

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            // disable default login 
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())


            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/error").permitAll() // ✅ FIX
                

                // admin 
                .requestMatchers("/products/add").hasRole("ADMIN")

                // user dan admin boleh checkout 
                .requestMatchers("/orders/admin").hasRole("ADMIN")
                .requestMatchers("/cart/**", "/orders/**").hasAnyRole("USER","ADMIN")
                .anyRequest().authenticated() // ✅ FIX
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // ✅ FIX

        return http.build();
    }
}