package ua.softserveinc.tc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.softserveinc.tc.dto.BookingDTO;

import java.util.List;

/**
 * Created by dima- on 06.06.2016.
 */
@Controller
public class UserBookingTimeController {
    @RequestMapping(value = "getnewbooking", method = RequestMethod.POST)
    public String getBooking(@RequestBody List<BookingDTO> some) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + some.get(1).getDate());

        return some.get(1).getDate();
    }
}
