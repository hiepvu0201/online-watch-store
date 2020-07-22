package com.group4.onlinewatchstore.entities.factory;

import java.time.LocalDateTime;
import java.util.List;

public class UserFactory {

    public static final String ADMIN="ADMIN";
    public static final String CUSTOMER="CUSTOMER";

    public UserFactory() {
    }

    public static final User getUser(String roleUser){
        switch (roleUser) {
            case "ADMIN":
                return new AdminUser();
            case "CUSTOMER":
                return new CustomerUser();
            default:
                return new CustomerUser();
        }
    }
}
