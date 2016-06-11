package ua.softserveinc.tc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.softserveinc.tc.constants.UserConstants;


@ComponentScan(basePackages = "ua.softserveinc.tc.service")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(UserConstants.Model.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getBCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("resources/css/**").permitAll()
                .antMatchers("resources/js/**").permitAll()
                .antMatchers(HttpMethod.GET,  "/registration").anonymous()
                .antMatchers(HttpMethod.GET, "/mykids").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/registerkid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.POST, "/registerkid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/editmykid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.POST, "/editmykid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/mybookings").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/report").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/report-all").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/report-parent").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/allkidslist").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/manager-confirm-booking").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/manager-edit-booking").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/statistics").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-add-manager").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-add-manager").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-add-room").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-add-room").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-edit-manager").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-edit-manager").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-edit-room").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-edit-room").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-update-manager").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-update-manager").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-update-room").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-update-room").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/adm-config").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-config").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/api/**").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/user/{id}").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/child").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/child/{id}").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/child/{id}/parent").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/user/search").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/child/search").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/booking/search").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/configuration").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/api/localization").hasRole(UserConstants.Role.ROLE_MANAGER)
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied")
                .and()


            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .failureUrl("/login?error")
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
                .and()

            .logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout");

        http.rememberMe().
                key("rem-me-key").
                rememberMeParameter("remember-me").
                rememberMeCookieName("my-remember-me").
                tokenValiditySeconds(286400);
    }

    @Bean
    public PasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
