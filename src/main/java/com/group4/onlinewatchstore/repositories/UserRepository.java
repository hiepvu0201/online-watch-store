package com.group4.onlinewatchstore.repositories;

import com.group4.onlinewatchstore.entities.factory.CustomerUser;
import com.group4.onlinewatchstore.entities.factory.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);
}
