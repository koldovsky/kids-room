package ua.softserveinc.tc.service.impl;


import org.opensaml.samlext.saml2mdui.DisplayName;
import org.opensaml.xml.schema.impl.XSAnyImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.ADFSParser;
import ua.softserveinc.tc.util.Log;

import javax.persistence.NonUniqueResultException;
import java.util.*;


@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);


    @Autowired
    private UserService userService;

    public Object loadUserBySAML(SAMLCredential credential)
            throws UsernameNotFoundException {
        Map<String, String> credentials = ADFSParser.parseCredentials(credential.getAttributes());
        String userEmail = credentials.get("emailaddress");
        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            user = new User();
            user.setEmail(userEmail);
            user.setActive(true);
            user.setRole(Role.USER);
            user.setConfirmed(true);
            user.setFirstName(credentials.getOrDefault("firstName", credentials.getOrDefault("name", "default")));
            user.setLastName(credentials.getOrDefault("lastName", "default"));
            user.setPassword("123");
            user.setPhoneNumber("+380000000000");
            userService.create(user);
            user = userService.getUserByEmail(userEmail);
            LOG.debug("New user: " + userEmail +  " is created");
        }
        LOG.debug("User: " + userEmail + " is logged in");

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isConfirmed(), accountNonExpired, credentialsNonExpired, user.isActive(), roles);

    }

}
