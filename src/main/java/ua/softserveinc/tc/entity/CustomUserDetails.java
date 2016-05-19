package ua.softserveinc.tc.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Chak on 19.05.2016.
 */
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
}
