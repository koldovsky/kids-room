package ua.softserveinc.tc.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;


@Controller
public class ConfirmBookingController {

    @Autowired
    private UserService userService;

    @GetMapping(BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW)
    public ModelAndView listBookings(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW);

        List<Room> rooms = userService.getActiveRooms(userService.getUserByEmail(principal.getName()));

        model.addAttribute("rooms", rooms);
        return modelAndView;
    }
}

