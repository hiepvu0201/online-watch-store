package com.group4.onlinewatchstore.controllers;

import com.group4.onlinewatchstore.entities.factory.User;
import com.group4.onlinewatchstore.entities.factory.UserFactory;
import com.group4.onlinewatchstore.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public User create(@Validated @RequestBody User user) throws Exception{
        String username = user.getUsername();
        if(username!=null&&!"".equals(username)){
            User tempUsername = userRepository.findByUsername(username);
            if(tempUsername!=null){
                throw new Exception("User "+username+" is already exist");
            }
        }
//        String pwd = user.getPassword();
//        String encryptPwd = passwordEncoder.encode(pwd);
//        user.setPassword(encryptPwd);
        return userRepository.save(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signIn(@Validated @RequestBody User u) {
        User user = userRepository.findByUsernameAndPassword(u.getUsername(),
                u.getPassword());

        if (user == null) {
            return ResponseEntity.ok(null);
        }

        UserFactory userFactory = new UserFactory();
        User userwithrole = userFactory.getUser(user.getRoles().get(0));

        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public List<User> getAllUser() {

        return userRepository.findAll();
    }
}
