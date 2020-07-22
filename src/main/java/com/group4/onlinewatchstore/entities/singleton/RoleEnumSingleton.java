package com.group4.onlinewatchstore.entities.singleton;

import java.util.ArrayList;
import java.util.List;

public enum RoleEnumSingleton {

    GETINSTANCE;

    public List<String> setAdmin() {
        List<String> roles = new ArrayList<>();
        roles.add("ADMIN");
        return roles;
    }

    public List<String> setCustomer() {
        List<String> roles = new ArrayList<>();
        roles.add("CUSTOMER");
        return roles;
    }
}
