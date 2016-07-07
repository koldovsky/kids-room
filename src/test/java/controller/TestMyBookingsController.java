package controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.softserveinc.tc.config.AppConfig;
import ua.softserveinc.tc.controller.user.MyBookingsController;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Nestor on 07.07.2016.
 */

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class TestMyBookingsController {
    @Mock
    private UserService userService;

    @Mock
    private BookingService bookingService;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private MyBookingsController myBookingsController;

    private MockMvc mockMvc;

    @Before
    public void initMockito(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(myBookingsController).build();
    }

    @Test
    public void testGetBookings() throws Exception{
        User user = mock(User.class);
        stub(userService.getUserByEmail(anyString())).toReturn(user);

        Booking b = mock(Booking.class);
        List<Booking> bookings = Arrays.asList(b);

        stub(bookingService.getBookings(
                new Date(),
                new Date(),
                user, BookingState.COMPLETED))
                .toReturn(bookings);

        BookingDto bDto = spy(new BookingDto());

        mockMvc.perform(get("mybookings/getbookings")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("dateLo", anyString())
                .param("dateHi", anyString()))
                .andExpect(status().isOk());

    }

}
