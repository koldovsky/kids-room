package ua.softserveinc.tc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Nestor on 29.06.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class UserServiceTest {
    @Resource
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    public void testGetUserByEmail(){
        assertNotNull(userService.getUserByEmail("user@softserveinc.com"));
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateUser(){
        User u = new User();
        u.setEmail("test@softserveinc.com");
        u.setPassword("password");
        u.setFirstName("Adam");
        u.setLastName("Tester");
        u.setRole(Role.USER);
        u.setPhoneNumber("+380679122354");

        userService.create(u);

        User afterPersist = userService.getUserByEmail("test@softserveinc.com");
        assertNotNull(afterPersist.getId());
        assertEquals("Tester", afterPersist.getLastName());
    }

    @Test
    @Transactional
    @Rollback
    public void testCreateWithEncoder(){
        String password = "password";
        String email = "test@softserveinc.com";


        User u = new User();
        u.setEmail(email);
        u.setPassword(password);

        u.setFirstName("Adam");
        u.setLastName("Tester");
        u.setRole(Role.USER);
        u.setPhoneNumber("+380679122354");

        userService.createWithEncoder(u);

        User after = userService.getUserByEmail(email);

        assertEquals(true, passwordEncoder.matches(password, after.getPassword()));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteAndFindAll(){
        User toBeDeleted = userService.findById(91L);
        userService.delete(toBeDeleted);
        List<User> all = userService.findAll();

        assertTrue(!all.contains(toBeDeleted));
    }

    @Test
    @Transactional
    @Rollback
    public void testDeleteById(){
        userService.deleteUserById(91L);
        try{
            userService.findById(91L);   //should throw an exception
            fail();
        }
        catch (ResourceNotFoundException rfe){
            //it's ok, test passed
        }
    }


}
