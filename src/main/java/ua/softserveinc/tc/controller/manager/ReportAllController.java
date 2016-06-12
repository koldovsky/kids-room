package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ReportConstants;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;
import java.util.Map;

import static ua.softserveinc.tc.util.DateUtil.toDate;

/**
 * Created by Demian on 10.05.2016.
 */
@Controller
public class ReportAllController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @ResponseBody
    @RequestMapping(value = "/report-all", method = RequestMethod.POST)
    public ModelAndView allParentsBookings(@RequestParam(value = ReportConstants.START_DATE) String startDate,
                                           @RequestParam(value = ReportConstants.END_DATE) String endDate,
                                           @RequestParam(value = ReportConstants.ROOM_ID) Long roomId) {

        ModelAndView modelAndView = new ModelAndView(ReportConstants.ALL_VIEW);
        ModelMap modelMap = modelAndView.getModelMap();

        Room room = roomService.findById(roomId);

        List<Booking> bookings = bookingService.getBookings(toDate(startDate), toDate(endDate), room, BookingState.COMPLETED);
        Map<User, Long> report = bookingService.generateAReport(bookings);

        modelMap.addAttribute(ReportConstants.REPORT, report);
        modelMap.addAttribute(ReportConstants.END_DATE, endDate);
        modelMap.addAttribute(ReportConstants.START_DATE, startDate);

        return modelAndView;
    }
}