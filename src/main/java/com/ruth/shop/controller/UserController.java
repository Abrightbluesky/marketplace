package com.ruth.shop.controller;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.ruth.shop.entity.User;
import com.ruth.shop.service.UserService;;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service){
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user){
        return service.create(user);
    }

    @GetMapping
    public List<User> getAll(){
        return service.findAll();
    }
}
