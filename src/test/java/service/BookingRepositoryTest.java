package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.repo.BookingRepository;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Nestor on 18.05.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
//в мануалі була своя конфігурація під окрему тестову базу
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class BookingRepositoryTest {

    @Resource
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Test
    public void testFindByPeriod() throws Exception{
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        List<Booking> lst = bookingRepository
                .findByBookingEndTimeBetween(c.getTime(), Calendar.getInstance().getTime());

        System.out.println("FindByPeriod: " + lst.size());
    }

    @Test
    public void testFindByRoomAndPeriod() throws Exception{
        Room r = roomService.findById(3L);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DAY_OF_MONTH, 1);
        List<Booking> lst = bookingRepository
                .findByRoomAndBookingEndTimeBetween(r, c.getTime(), Calendar.getInstance().getTime());
        System.out.println("FindByRoomAndPeriod: " + lst.size());

    }

    @Test
    public void testFindByUserAndRoomAndPeriod() throws Exception{
        User user = userService.findById(1L);
        Room r = roomService.findById(3L);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        List<Booking> lst = bookingRepository
                .findByRoomAndUserAndBookingEndTimeBetween(r, user, c.getTime(), Calendar.getInstance().getTime());

        System.out.println("FindByUserAndRoomAndPeriod: " + lst.size());
    }

    @Test
    public void testFindByUserAndPeriod() throws Exception{
        User user = userService.findById(1L);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        List<Booking> lst = bookingRepository
                .findByUserAndBookingEndTimeBetween(user, c.getTime(), Calendar.getInstance().getTime());

        System.out.println("FindByUserAndPeriod: " + lst.size());
    }
}
