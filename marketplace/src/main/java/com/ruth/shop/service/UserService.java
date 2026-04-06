package com.ruth.shop.service;
import org.springframework.stereotype.Service;
import java.util.List;
import com.ruth.shop.entity.User;
import com.ruth.shop.repository.UserRepository;

@Service

public class UserService {
    private final UserRepository repository;
    public UserService(UserRepository repository){
        this.repository = repository;
    }

    public User create(User user){
        return repository.save(user);
    }

    public List<User> findAll(){
        return repository.findAll();
    }
}
