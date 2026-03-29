package com.ruth.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.ruth.shop.dto.LoginRequest;
import com.ruth.shop.dto.RegisterRequest;
import com.ruth.shop.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    // LOGIN
   @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

    System.out.println("=== CONTROLLER HIT ===");
    System.out.println("EMAIL: " + request.getEmail());
    System.out.println("PASSWORD: " + request.getPassword());

    return authService.login(request);
}
}