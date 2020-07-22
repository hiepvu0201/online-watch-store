package com.group4.onlinewatchstore.entities.factory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AdminUser extends User{

    public AdminUser( User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLast_name(user.getLast_name());
        this.setAddress(user.getAddress());
        this.setPassword(user.getPassword());
        this.setPhone(user.getPhone());
        this.setRoles(user.getRoles());
    }

    public AdminUser() {
    }

    @Override
    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        return roles;
    }
}
