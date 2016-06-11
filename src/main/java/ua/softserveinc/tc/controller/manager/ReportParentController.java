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
import ua.softserveinc.tc.constants.RoomConstants;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.toDate;

/**
 * Created by Demian on 08.05.2016.
 */
@Controller
public class ReportParentController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @ResponseBody
    @RequestMapping(value = "/report-parent", method = RequestMethod.POST)
    public ModelAndView parentBookings(@RequestParam(value = ReportConstants.PARENT_EMAIL) String email,
                                       @RequestParam(value = ReportConstants.DATE_THEN) String dateThen,
                                       @RequestParam(value = ReportConstants.DATE_NOW) String dateNow,
                                       @RequestParam(value = RoomConstants.View.ROOM_ID) Long roomId) {

        ModelAndView modelAndView = new ModelAndView(ReportConstants.PARENT_VIEW);
        ModelMap modelMap = modelAndView.getModelMap();

        Room room = roomService.findById(roomId);
        User parent = userService.getUserByEmail(email);

        List<Booking> bookings = bookingService.getBookings(toDate(dateThen), toDate(dateNow),
                parent, room, BookingState.COMPLETED);
        Long sumTotal = bookingService.getSumTotal(bookings);

        modelMap.addAttribute(ReportConstants.PARENT, parent);
        modelMap.addAttribute(ReportConstants.DATE_NOW, dateNow);
        modelMap.addAttribute(ReportConstants.BOOKINGS, bookings);
        modelMap.addAttribute(ReportConstants.DATE_THEN, dateThen);
        modelMap.addAttribute(ReportConstants.SUM_TOTAL, sumTotal);

        return modelAndView;
    }
}