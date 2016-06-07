package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dto.BookingDtosss;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

/**
 * Created by Петришак on 04.06.2016.
 */

@Controller
public class BookingEditController {
    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    BookingService bookingService;

    @RequestMapping(value = BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW)
    public ModelAndView changeBooking(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> listRoom = roomService.findByManger(currentManager);
        Room room = roomService.getRoomByManager(currentManager);
        List<Booking> listBooking = bookingService.getTodayBookingsByRoom(room);
        modelAndView.addObject("listRoom", listRoom);
        modelAndView.addObject("listBooking", listBooking);
        return modelAndView;
    }

    @RequestMapping(value = BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW, method = RequestMethod.POST,
            consumes = "application/json")
    public
    @ResponseBody
    Boolean change(@RequestBody BookingDtosss bookingDto) {
        Booking booking = bookingService.findById(bookingDto.getId());
        Room room = booking.getIdRoom();
        Date startTime = toDateAndTime(bookingDto.getStartTime());
        Date endTime = toDateAndTime(bookingDto.getEndTime());
        bookingService.isPeriodAvailable(room, startTime, endTime);
        return false;
    }
}
