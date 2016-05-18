package ua.softserveinc.tc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
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
    @Autowired
    BookingDao bookingDao;


    @RequestMapping(value = "/listChildren")
    public ModelAndView parentBookings(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listChildren");

   //     ModelMap modelMap = modelAndView.getModelMap();
 //       modelAndView.addObject("bookedJSP", new Booking());
        List<Booking> listBooking = bookingService.getBookingsOfThisDay();
        model.addAttribute("listBooking", listBooking);
        DateFormat df = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        String date = df.format(listBooking.get(0).getBookingStartTime());
        model.addAttribute("BookingPerDay", date);
        return modelAndView;
    }
    @RequestMapping(value = "/listChildren", method = RequestMethod.POST)
    public String p(Model model, @RequestParam("date") String date) throws ParseException{
        List<Booking> listBooking = bookingService.getBookingsByDay(date);
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("BookingPerDay",  date);
        return  "listChildren";
    }

    @RequestMapping(value = "/setTime", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String setingBookings(@RequestBody BookingDTO bookingDTO) throws ParseException {
        Booking booking = bookingService.updatingBooking(bookingDTO);
        BookingDTO jsonBooking = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(jsonBooking);
    }


    @RequestMapping(value = "getCompan/{a}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getRaandom(@PathVariable Long a) {
        Booking b = bookingService.findById(a);
        BookingDTO jsb = new BookingDTO(b);
        Gson gson = new Gson();
        return  gson.toJson(jsb);
    }
}
