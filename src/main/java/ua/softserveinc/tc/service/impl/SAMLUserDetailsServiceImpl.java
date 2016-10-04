package ua.softserveinc.tc.service.impl;


import org.opensaml.samlext.saml2mdui.DisplayName;
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
import ua.softserveinc.tc.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class SAMLUserDetailsServiceImpl implements SAMLUserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(SAMLUserDetailsServiceImpl.class);


    @Autowired
    private UserService userService;

    public Object loadUserBySAML(SAMLCredential credential)
            throws UsernameNotFoundException {

        LOG.debug("GetNameID " + credential.getNameID().getValue());
        LOG.debug("LocalEntityID " + credential.getLocalEntityID());
        LOG.debug("Credential " + credential.toString());
        LOG.debug("Display-Name: " + credential.getAttributeAsString("DisplayName"));
        LOG.debug("User-Principal-Name: " + credential.getAttributeAsString("UserPrincipalName"));
        LOG.debug("E-Mail-Address:  " + credential.getAttributeAsString("EmailAddress"));

        String userEmail = credential.getNameID().getValue();

        User user = userService.getUserByEmail(userEmail);
        if(user == null) {
            user = new User();
            user.setEmail(userEmail);
            user.setActive(true);
            user.setRole(Role.USER);
            user.setConfirmed(true);
            user.setFirstName("Default");
            user.setLastName("Default");
            user.setPassword("123");
            user.setPhoneNumber("+380000000000");
            userService.create(user);
            user = userService.getUserByEmail(userEmail);
        }

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        Set<GrantedAuthority> roles = new HashSet();
        roles.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isConfirmed(), accountNonExpired, credentialsNonExpired, user.isActive(), roles);


    }

}
