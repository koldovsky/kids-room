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

    @RequestMapping(value = "/parentbookings", method = RequestMethod.GET, params = {"parentEmail"})
    public @ResponseBody ModelAndView parentBookings(Principal principal,
                                                     @RequestParam(value = "parentEmail") String parentEmail)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("parentbookings");
        ModelMap modelMap = modelAndView.getModelMap();

        User parent = userService.getUserByEmail(parentEmail);
        List<Booking> bookingList = bookingService.getBookingsByUser(parent);
        modelMap.addAttribute("bookings", bookingList);
        modelMap.addAttribute("parent", parent);
        return modelAndView;
    }
}
