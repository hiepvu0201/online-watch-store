package com.group4.onlinewatchstore.entities.factory;

public class UserFactory {

    public UserFactory() {
    }

    public static final User getUser(String roleUser){
        switch (roleUser) {
            case "ADMIN":
                return new AdminUser();
            case "CUSTOMER":
                return new CustomerUser();
            default:
                throw new IllegalArgumentException("This role is unsupported");
        }
    }
}
