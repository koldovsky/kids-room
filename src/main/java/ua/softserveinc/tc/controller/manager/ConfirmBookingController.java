package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.repo.BookingRepository;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * Created by Петришак on 08.05.2016.
 */
@Controller
public class ConfirmBookingController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;

    @RequestMapping(value = BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW)
    public ModelAndView listBookings(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> rooms = currentManager.getRooms();
        model.addAttribute("rooms", rooms);
        return modelAndView;
    }

    /*@RequestMapping(value="manager-test")
    public ModelAndView managersTest(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("manager-test");
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> listRoom = currentManager.getRooms();
        model.addAttribute("rooms", listRoom);
        return modelAndView;

    }*/

    @ResponseBody
    @RequestMapping(value = "getAmountOfChildren/{roomId}", method = RequestMethod.GET)
    public Long getAmountOfChildrenInTheRoom(@PathVariable Long roomId) {
        Room room = roomService.findById(roomId);
        return bookingRepository.countByRoomAndBookingState(room, BookingState.ACTIVE);
    }
}

