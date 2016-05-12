package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;

import java.util.List;

/**
 * Created by Demian on 10.05.2016.
 */
@Controller
public class AllParentsBookingsPageController
{
    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "allParentsBookings", method = RequestMethod.GET,
            params = {"dateThen", "dateNow"})

    public @ResponseBody ModelAndView allParentsBookings(@RequestParam(value = "dateThen") String dateThen,
                                                         @RequestParam(value = "dateNow") String dateNow)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("allParentsBookings");
        ModelMap modelMap = modelAndView.getModelMap();

        List<Booking> bookings = bookingService.getBookingsByRangeOfTime(dateThen, dateNow);
        modelMap.addAttribute("bookings", bookings);
        return modelAndView;
    }
}
