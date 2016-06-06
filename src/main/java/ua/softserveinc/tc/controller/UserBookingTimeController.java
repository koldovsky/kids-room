package ua.softserveinc.tc.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.softserveinc.tc.dto.BookingDTO;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * Created by dima- on 06.06.2016.
 */
@Controller
public class UserBookingTimeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "makenewbooking", method = RequestMethod.POST)
    public @ResponseBody String getBooking(@RequestBody List<BookingDTO> dtos, Principal principal) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + dtos.get(1).getDate());
        dtos.forEach(dto -> {
            dto.setUser(userService.getUserByEmail(principal.getName()));
            dto.setChild(childService.findById(dto.getKidId()));
            dto.setRoom(roomService.findById(dto.getRoomId()));
        });

        return new Gson()
                .toJson(bookingService.persistBookingsFromDTOandSetID(dtos));
    }
}
