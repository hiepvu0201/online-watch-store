package com.group4.onlinewatchstore.repositories;

import com.group4.onlinewatchstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUsername(String username);
}
