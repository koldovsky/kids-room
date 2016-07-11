package ua.softserveinc.tc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.entity.Room;

import javax.annotation.Resource;
import java.util.Calendar;


/**
 * Created by Nestor on 06.07.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class RoomServiceTest {

    @Resource
    private RoomService roomService;

    @Test
    public void testBlockedPeriods(){
        Room room = roomService.findById(1L);
        Calendar today = Calendar.getInstance();
        Calendar weekAgo = Calendar.getInstance();
        weekAgo.add(Calendar.WEEK_OF_MONTH, -1);

        long s = System.nanoTime();
        System.out.println(roomService.getBlockedPeriods(room, weekAgo, today));
        System.out.println(System.nanoTime() - s);

    }
}
