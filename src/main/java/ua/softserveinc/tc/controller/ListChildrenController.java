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
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    BookingDao bookingDao;

    @Autowired
    ChildService child;


    @RequestMapping(value = "/listChildren")
    public ModelAndView parentBookings(Model model) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("listChildren");
        DateFormat df = new SimpleDateFormat(DateConst.SHORT_DATE_FORMAT);
        Calendar toDay = Calendar.getInstance();
        Date date = toDay.getTime();
        Date date1 = new Date("2016/06/20");
        String dateString = df.format(date1);
        List<Booking> listBooking = bookingService.getBookingsByDay(dateString);
        model.addAttribute("listBooking", listBooking);
        model.addAttribute("BookingPerDay", dateString);
        model.addAttribute("nowTime", date);
        return modelAndView;
    }

    @RequestMapping(value = "cancelBook/{idBooking}", method = RequestMethod.GET)
    public @ResponseBody String cancelBooking (Model model,
                                               @PathVariable Long idBooking) throws ParseException{
        Booking booking = bookingService.findById(idBooking);
        booking.setCancelled(true);
        booking.setSum(0);
        bookingService.update(booking);
        BookingDTO bookingDTO = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTO);
    }

    @RequestMapping(value = "/setTime", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String setingBookingsStartTime(@RequestBody BookingDTO bookingDTO) throws ParseException {
        Booking booking = bookingService.confirmBookingStartTime(bookingDTO);
        BookingDTO bookingDTOtoJson = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTOtoJson);
    }

    @RequestMapping(value = "/setEndTime", method = RequestMethod.POST, consumes = "application/json")
    public
    @ResponseBody
    String setingBookingsEndTime(@RequestBody BookingDTO bookingDTO) throws ParseException {
        Booking booking = bookingService.confirmBookingEndTime(bookingDTO);
        BookingDTO bookingDTOtoJson = new BookingDTO(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTOtoJson);
    }

}
