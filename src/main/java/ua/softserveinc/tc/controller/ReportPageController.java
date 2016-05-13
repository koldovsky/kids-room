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

import java.util.HashMap;
import java.util.List;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportPageController
{
    @Autowired
    RoomService roomService;

    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public ModelAndView report()
    {
        ModelAndView model = new ModelAndView();
        model.setViewName(ReportConst.REPORT_VIEW);
        ModelMap modelMap = model.getModelMap();

        String dateNow = bookingService.getCurrentDate();
        String dateThen = bookingService.getDateMonthAgo();
        List<Booking> bookings = bookingService.getActiveUsersForRangeOfTime(dateThen, dateNow);

        modelMap.addAttribute(ReportConst.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConst.BOOKINGS, bookings);
        modelMap.addAttribute(ReportConst.DATE_THEN, dateThen);

        //TODO:Забрати звідси цей костиль
        //Початок:
        Room room = roomService.findById(new Long(1));
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(2, 300);
        map.put(4, 500);
        map.put(6, 600);
        room.setPricing(map);
        roomService.update(room);
        //Кінець

        return model;
    }
}
