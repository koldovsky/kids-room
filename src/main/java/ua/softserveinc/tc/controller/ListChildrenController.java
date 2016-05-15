package ua.softserveinc.tc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.JsonConvert.JsonBooking;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.UserService;

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

        String ss;
      // if(listBooking.size()!=0) {
           ss = listBooking.get(0).extractMonthDayAndYear();
           modelMap.addAttribute("BookingPerDay", ss);
           return modelAndView;

     //  }else return new ModelAndView("listChildren");


    }
    @RequestMapping(value = "/result")
    public ModelAndView setingBookings(@ModelAttribute Booking booking) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("result");

        Booking b = bookingService.findById(booking.getIdBook());
        b.setBookingStartTime(booking.getBookingStartTime());
        bookingService.update(b);
        modelAndView.addObject("bookedJSP", booking);
        return modelAndView;
    }
    @RequestMapping(value = "getCompan/{a}",  method = RequestMethod.GET)
    public @ResponseBody
    String getRaandom(@PathVariable Long a) {
        Booking b = bookingService.findById(a);
        JsonBooking jsb = new JsonBooking(b.extractHourAndMinuteFromStartTime(), a);
        Gson gson = new Gson();
        String json = gson.toJson(jsb);

        return json;
    }
    @RequestMapping(value = "setBookingTime/{date}",  method = RequestMethod.POST)
        public
    @ResponseBody ModelAndView setBookingTime(@PathVariable String date){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listChildren");
        List<Booking> bookingByDay = bookingService.getBookingsByDay(date);
        ModelMap modelMap = modelAndView.getModelMap();
        modelMap.addAttribute("listBooking", bookingByDay);
        return modelAndView;
    }


}
