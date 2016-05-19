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
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.UserService;

import static org.junit.Assert.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Nestor on 18.05.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
//в мануалі була своя конфігурація під окрему тестову базу
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class BookingServiceTest {
    @Resource
    BookingService bookingService;

    @Autowired
    UserService userService;

    @Test
    public void testSumTotal() throws Exception{
        List<Booking> bookings = new ArrayList<>();
        bookings.add(new Booking());
        bookings.add(new Booking());
        bookings.forEach(booking -> booking.setSum(10));

        assertEquals("Should be 20", 20, bookingService.getSumTotal(bookings));

    }
}
