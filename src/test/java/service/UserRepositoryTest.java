package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.repo.UserRepository;
import ua.softserveinc.tc.service.RoomService;

import javax.annotation.Resource;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by Nestor on 09.06.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class UserRepositoryTest {
    @Resource
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Test
    public void testGetActiveUsers(){
        Room r = roomService.findById(1L);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        System.out.println(userRepository.getActiveUsers(r, c.getTime(), Calendar.getInstance().getTime()).size());
    }

    @Test
    public void testFindByEmail(){
        User u = userRepository.findByEmail("user@softserveinc.com");
        assertEquals("Should be equal", "Alan Bom", u.getFullName());
    }
}
