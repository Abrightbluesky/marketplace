package com.ruth.shop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ruth.shop.dto.LoginRequest;
import com.ruth.shop.dto.RegisterRequest;
import com.ruth.shop.entity.Role;
import com.ruth.shop.entity.User;
import com.ruth.shop.repository.UserRepository;
import com.ruth.shop.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 🔐 REGISTER
    public String register(RegisterRequest request){

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        return "User registered";
    }

    // 🔑 LOGIN (CLEAN & SAFE)
    public String login(LoginRequest request){

        // 1. cek user
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if(user == null){
            return "User not found";
        }

        System.out.println("ROLE: " + user.getRole());

        // 2. cek password
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!isMatch){
            return "Invalid password";
        }

        // 3. generate token
        return jwtUtil.generateToken(
            user.getEmail(),
            user.getRole().name());
    };
}