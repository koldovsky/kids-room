package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Петришак on 08.05.2016.
 */
@Controller
public class ListChildrenController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    ChildService childService;

    @RequestMapping(value = "/listChildren", method = RequestMethod.GET)
    public ModelAndView parentBookings(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listChildren");
        ModelMap modelMap = modelAndView.getModelMap();
        modelAndView.addObject("bookedJSP", new Booking());

        List<Booking> listBooking = bookingService.getBookingsOfThisDay();
        modelMap.addAttribute("listBooking", listBooking);
        return modelAndView;
    }
    @RequestMapping(value = "/result")
    public ModelAndView setingBookings(@ModelAttribute Booking booking) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");
        System.out.println(booking.getIdBook() + ""+ booking.getBookingStartTime());
        Booking b = bookingService.findById(booking.getIdBook());
        b.setBookingStartTime(booking.getBookingStartTime());
        bookingService.update(b);
        modelAndView.addObject("bookedJSP", booking);
        return modelAndView;
    }


}
