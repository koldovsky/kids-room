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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ua.softserveinc.tc.constants.UserConstants;

import javax.sql.DataSource;


@ComponentScan(basePackages = {"ua.softserveinc.tc.service", "ua.softserveinc.tc.config"})
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier(UserConstants.Model.USER_DETAILS_SERVICE)
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

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
                .antMatchers(HttpMethod.GET, "/registration").anonymous()
                .antMatchers(HttpMethod.GET, "/profile").authenticated()
                .antMatchers(HttpMethod.GET, "/mykids").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/registerkid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.POST, "/registerkid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/editmykid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.POST, "/editmykid").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/mybookings").hasRole(UserConstants.Role.ROLE_USER)
                .antMatchers(HttpMethod.GET, "/manager-**").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.POST, "/manager-**").hasRole(UserConstants.Role.ROLE_MANAGER)
                .antMatchers(HttpMethod.GET, "/adm-**").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.POST, "/adm-**").hasRole(UserConstants.Role.ROLE_ADMINISTRATOR)
                .antMatchers(HttpMethod.GET, "/api/**").hasRole(UserConstants.Role.ROLE_MANAGER)
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
    public PasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

}
