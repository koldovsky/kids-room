package ua.softserveinc.tc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.ReportConst;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Demian on 28.05.2016.
 */
@Controller
public class StatisticsController
{
    @Autowired
    DateUtil dateUtil;

    @Autowired
    RoomService roomService;

    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/adm-statistics", method = RequestMethod.GET)
    public ModelAndView statistics()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName("adm-statistics");
        ModelMap modelMap = model.getModelMap();

        String dateNow = dateUtil.getStringDate(dateUtil.currentDate());
        String dateThen = dateUtil.getStringDate(dateUtil.dateMonthAgo());

        List<Room> rooms = roomService.findAll();
        List<Booking> bookings = bookingService.getBookingsByRangeOfTime(dateThen, dateNow);

        HashMap<Room, Long> statistics = bookingService.generateAReport(rooms, bookings);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);
        modelMap.addAttribute("statistics", statistics);

        return model;
    }
}