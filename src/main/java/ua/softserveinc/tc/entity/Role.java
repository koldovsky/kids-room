package ua.softserveinc.tc.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER("USER"),
    MANAGER("MANAGER"),
    ADMINISTRATOR("ADMINISTRATOR");
    String name;


    Role(final String name){
        this.name=name;
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }
}
