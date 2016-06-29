package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.UserService;

import static org.junit.Assert.*;

import javax.annotation.Resource;

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
        u.setPhoneNumber("380679122354");

        userService.create(u);

        User afterPersist = userService.getUserByEmail("test@softserveinc.com");
        assertNotNull(afterPersist.getId());
        assertEquals("Tester", afterPersist.getLastName());

    }
}
