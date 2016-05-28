package ua.softserveinc.tc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.ModelConstants.UsersConst;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nestor on 14.05.2016.
 */

@Controller
public class MyBookingsController {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/mybookings", method = RequestMethod.GET)
    public ModelAndView getMyBookings(Principal principal){

        //TODO: юзеру дати можливість вибрати дату по AJAX-у
        ModelAndView model = new ModelAndView();
        model.setViewName(UsersConst.MY_BOOKINGS_VIEW);

        ModelMap modelMap = model.getModelMap();



        return model;
    }


    @RequestMapping(value = "mybookings/getbookings", method = RequestMethod.GET)
    public @ResponseBody String getBookings(
                       @RequestParam(value = "dateLo") String dateLo,
                       @RequestParam(value = "dateHi") String dateHi,
                       Principal principal) throws ParseException{

        User currentUser = userService.getUserByEmail(principal.getName());
        List<Booking> myBookings = bookingService.getBookingsByUserByRangeOfTime(currentUser, dateLo, dateHi);
        List<BookingDTO> dtos = new ArrayList<>();
        myBookings.forEach((booking -> dtos.add(new BookingDTO(booking))));

        Gson gson = new Gson();
        return gson.toJson(dtos);
    }
}
