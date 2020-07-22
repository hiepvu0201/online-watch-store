package com.group4.onlinewatchstore.entities.factory;

import com.group4.onlinewatchstore.entities.singleton.RoleEnumSingleton;

import java.util.List;

public class CustomerUser extends User {

    private long salary;

    private long beginDate;

    public CustomerUser(User user) {
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

    public CustomerUser(){
    }

    @Override
    public List<String> getRoles() {
//        List<String> roles = new ArrayList<>();
//        roles.add("CUSTOMER");
//        return roles;

        //Enum Singleton
        RoleEnumSingleton enumSingleton = RoleEnumSingleton.GETINSTANCE;
        return enumSingleton.setCustomer();
    }
}
