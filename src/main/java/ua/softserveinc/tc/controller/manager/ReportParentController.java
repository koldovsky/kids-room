package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
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
    @RequestMapping(value = "/manager-report-parent", method = RequestMethod.GET)
    public ModelAndView parentBookings(@RequestParam(value = ReportConstants.START_DATE) String startDate,
                                       @RequestParam(value = ReportConstants.END_DATE) String endDate,
                                       @RequestParam(value = ReportConstants.ROOM_ID) Long roomId,
                                       @RequestParam(value = ReportConstants.EMAIL) String email,
                                       Principal principal) {

        Room room = roomService.findById(roomId);
        User manager = userService.getUserByEmail(principal.getName());

        if (!room.getManagers().contains(manager)) {
            throw new AccessDeniedException("You don't have access to this page");
        }

        User parent = userService.getUserByEmail(email);
        List<Booking> bookings = bookingService.getBookings(toDate(startDate), toDate(endDate),
                parent, room, BookingState.COMPLETED);

        Long sumTotal = bookingService.getSumTotal(bookings);

        ModelAndView modelAndView = new ModelAndView(ReportConstants.PARENT_VIEW);
        ModelMap modelMap = modelAndView.getModelMap();

        modelMap.addAttribute(ReportConstants.ROOM, room);
        modelMap.addAttribute(ReportConstants.PARENT, parent);
        modelMap.addAttribute(ReportConstants.END_DATE, endDate);
        modelMap.addAttribute(ReportConstants.BOOKINGS, bookings);
        modelMap.addAttribute(ReportConstants.SUM_TOTAL, sumTotal);
        modelMap.addAttribute(ReportConstants.START_DATE, startDate);
        modelMap.addAttribute("pageChecker","reportParent"); //value for checking the page in header.jsp

        return modelAndView;
    }
}