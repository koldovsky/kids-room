package ua.softserveinc.tc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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


    @RequestMapping(value = "/listChildren")
    public ModelAndView parentBookings(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listChildren");
        ModelMap modelMap = modelAndView.getModelMap();
        modelAndView.addObject("bookedJSP", new Booking());
        List<Booking> listBooking = bookingService.getBookingsOfThisDay();
        modelMap.addAttribute("listBooking", listBooking);
        DateFormat df = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        String date = df.format(listBooking.get(0).getBookingStartTime());
        modelMap.addAttribute("BookingPerDay", date);
        return modelAndView;
    }

    @RequestMapping(value = "/setTime", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String setingBookings(@RequestBody BookingDTO bookingDTO) {
        Booking booking = bookingService.updatingBooking(bookingDTO);
        bookingService.update(booking);
        BookingDTO jsonBooking = new BookingDTO(booking);
        Gson gson = new Gson();
        String json = gson.toJson(jsonBooking);
        return json;
    }


    @RequestMapping(value = "getCompan/{a}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getRaandom(@PathVariable Long a) {
        Booking b = bookingService.findById(a);
        BookingDTO jsb = new BookingDTO(b);
        Gson gson = new Gson();
        String json = gson.toJson(jsb);
        return json;

    }
}
