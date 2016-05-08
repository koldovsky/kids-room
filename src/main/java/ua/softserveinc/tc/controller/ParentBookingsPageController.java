package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ParentBookingsPageController
{
    @Autowired
    BookingService bookingService;
    @Autowired
    UserService userService;

    @RequestMapping(value = "/parentbookings", method = RequestMethod.GET)
    public ModelAndView parentBookings(Principal principal)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parentbookings");
        ModelMap modelMap = modelAndView.getModelMap();

        /*TODO:
        *
        * implement getting User's email from report.jsp page
        * on that page every div has got its own email
        * this email has to be sent on this page
        *
        */
        User user = userService.getUserByEmail("user@gmail.com");
        List<Booking> bookingList = bookingService.getBookingsByUser(user);
        modelMap.addAttribute("bookings", bookingList);
        return modelAndView;
    }
}
