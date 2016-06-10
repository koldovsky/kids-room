package ua.softserveinc.tc.controller.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.softserveinc.tc.constants.BookingConstants;
import ua.softserveinc.tc.dao.BookingDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
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

    @Autowired
    ChildService childService;

    @Autowired
    BookingDao bookingDao;

    @RequestMapping(value = BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW)
    public ModelAndView changeBooking(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> listRoom = roomService.findByManger(currentManager);
        Room room = listRoom.get(0);
        List<Booking> bookings = bookingService.getBookings(workingHours().get(0),
                workingHours().get(1),
                room, BookingState.BOOKED);
        Date date = currentDate().getTime();
        List<Child> children = childService.findAll();
        model.addAttribute("rooms", listRoom);
        model.addAttribute("listChild", children);
        model.addAttribute("today", date);
        model.addAttribute("listBooking", bookings);
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
    @RequestMapping(value = "create-booking", method = RequestMethod.POST,  consumes = "application/json")
    public
    @ResponseBody
    Boolean createBooking (Principal principal, @RequestBody BookingDto bookingDto){
        User manager = userService.getUserByEmail(principal.getName());
        Room room = roomService.findByManger(manager).get(0);
        Date dateLo = toDateAndTime(bookingDto.getStartTime());
        Date dateHi = toDateAndTime(bookingDto.getEndTime());

        bookingDto.setDateStartTime(dateLo);
        bookingDto.setDateEndTime(dateHi);

        Child child = childService.findById(bookingDto.getIdChild());
        // TODO: 10.06.2016  get booking by child
        if(roomService.isPeriodAvailable(dateLo, dateHi, room)){
            Booking booking = bookingDto.getBookingObject();
            booking.setIdRoom(room);
            booking.setIdChild(child);
            booking.setIdUser(child.getParentId());
            bookingDao.create(booking);
            bookingDao.update(booking);
            return true;
        }else {
            return false;
        }
    }


}
