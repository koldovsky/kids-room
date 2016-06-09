package ua.softserveinc.tc.controller.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.*;

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
        modelAndView.setViewName(BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> listRoom = roomService.findByManger(currentManager);
        Room room = listRoom.get(0);
        List<Booking> listBooking = bookingService.getBookings(workingHours().get(0),
                workingHours().get(1)
                ,room);
        List<Booking> filterBookedList = bookingService.filterByState(listBooking, BookingState.BOOKED);
        Date date = currentDate().getTime();
        model.addAttribute("listRoom", listRoom);
        model.addAttribute("today", date);
        model.addAttribute("listBooking", filterBookedList);
        return modelAndView;
    }

    @RequestMapping (value = "manager-edit-booking/{date}",
            method = RequestMethod.GET)
    public @ResponseBody
        String bookingsByDay(Principal principal,@PathVariable String date){
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> listRoom = roomService.findByManger(currentManager);
        Room room = listRoom.get(0);
        List<Booking> bookings = bookingService.getBookings(setStartTime(toDate(date)), setEndTime(toDate(date)), room);
        Gson gson = new Gson();
        List<BookingDto> bokDto = new ArrayList<>();
        for (Booking boking: bookings) {
            bokDto.add(new BookingDto(boking));
        }
     /*   List<User> lst = userService.findAll();
          return   gson.toJson(lst.stream()
                    .map(UserDto::new)
                    .collect(Collectors.toList()));*/

        return  gson.toJson(bokDto);
    }

    @RequestMapping(value = "change-booking", method = RequestMethod.POST,
            consumes = "application/json")
    public
    @ResponseBody
    Boolean change(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.findById(bookingDto.getId());
        Room room = booking.getIdRoom();
        Date startTime = toDateAndTime(bookingDto.getStartTime());
        Date endTime = toDateAndTime(bookingDto.getEndTime());
        if(roomService.isPeriodAvailable(startTime, endTime, room)) {
            booking.setBookingEndTime(endTime);
            booking.setBookingStartTime(startTime);
            bookingService.update(booking);
            return true;
        }
        return false;
    }
}
