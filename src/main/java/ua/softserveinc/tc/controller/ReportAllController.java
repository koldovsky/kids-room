package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.UserService;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Demian on 10.05.2016.
 */
@Controller
public class ReportAllController
{
    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;


    @RequestMapping(value = "/manager-report-all", method = RequestMethod.GET,
            params = {ReportConst.DATE_THEN, ReportConst.DATE_NOW})

    public @ResponseBody ModelAndView allParentsBookings(@RequestParam(value = ReportConst.DATE_THEN) String dateThen,
                                                         @RequestParam(value = ReportConst.DATE_NOW) String dateNow) throws ParseException
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(ReportConst.ALL_VIEW);
        ModelMap modelMap = modelAndView.getModelMap();

        List<User> users = userService.getActiveUsers(dateThen, dateNow);
        List<Booking> bookings = bookingService.getBookingsByRangeOfTime(dateThen, dateNow);
        HashMap<User, Long> report = bookingService.generateAReport(users, bookings);

        modelMap.addAttribute(ReportConst.REPORT, report);
        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);

        return modelAndView;
    }
}