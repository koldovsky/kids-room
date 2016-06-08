package ua.softserveinc.tc.controller.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by dima- on 06.06.2016.
 */
@Controller
public class BookingTimeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "makenewbooking", method = RequestMethod.POST)
    public
    @ResponseBody
    String getBooking(@RequestBody List<BookingDto> dtos, Principal principal) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + dtos.get(0).getStartTime());
        dtos.forEach(dto -> {
            dto.setUser(userService.getUserByEmail(principal.getName()));
            dto.setChild(childService.findById(dto.getKidId()));
            dto.setRoom(roomService.findById(dto.getRoomId()));
        });

        return new Gson()
                .toJson(bookingService.persistBookingsFromDtoAndSetId(dtos));
    }

    @RequestMapping(value = "/disabled")
    public
    @ResponseBody
    String getDisabledTime(@RequestParam Long roomID,
                           @RequestParam String period) {
        Map<String, String> blockedPeriods;
        Room room = roomService.findById(roomID);
        switch (period) {
            case "day":
                blockedPeriods = roomService
                        .getBlockedPeriodsForDay(room, Calendar.getInstance());
                break;
            case "week":
                blockedPeriods = roomService
                        .getBlockedPeriodsForWeek(room);
                break;
            default:
                throw new ResourceNotFoundException();
        }

        return new Gson()
                .toJson(blockedPeriods);
    }
}
