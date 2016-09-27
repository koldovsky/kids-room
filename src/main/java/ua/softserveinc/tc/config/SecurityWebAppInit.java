package ua.softserveinc.tc.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class SecurityWebAppInit extends AbstractSecurityWebApplicationInitializer {

    public SecurityWebAppInit(){
        super(SecurityConfig.class);
    }

}