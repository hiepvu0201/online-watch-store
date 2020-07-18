package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.Product;
import com.group4.onlinewatchstore.entities.User;
import com.group4.onlinewatchstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public User create(@Validated @RequestBody User user){
        String pwd = user.getPassword();
        String encryptPwd = passwordEncoder.encode(pwd);
        user.setPassword(encryptPwd);
        return userRepository.save(user);
    }

    @GetMapping("/")
    public List<User> getAllUser() {

        return userRepository.findAll();
    }
}
