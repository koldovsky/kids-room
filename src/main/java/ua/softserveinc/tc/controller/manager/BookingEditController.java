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
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        List<Room> listRoom = roomService.findByManager(currentManager);
        Room room = listRoom.get(0);
        List<Booking> bookings = bookingService.getBookings(workingHours().get(0),
                workingHours().get(1),
                room, BookingState.BOOKED);
        Date date = toDate(dateNow());
        List<Child> children = childService.findAll();
        List<User> users = userService.findAll();
        Set<Child> kids = userService.findById(1L).getChildren();
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", users);
        model.addAttribute("kids", kids);
        model.addAttribute("listChild", children);
        model.addAttribute("today", date);
        model.addAttribute("listBooking", bookings);
        return modelAndView;
    }

    @RequestMapping (value = "manager-edit-booking/{date}/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public String bookingsByDay(@PathVariable String date,
                                @PathVariable Long id){
        Room room = roomService.findById(id);
        List<Booking> bookings = bookingService.getBookings(setStartTime(toDate(date)), setEndTime(toDate(date)), room);
        Gson gson = new Gson();
        return  gson.toJson(bookings.stream()
                .map(BookingDto::new)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "change-booking", method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public Boolean change(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.findById(bookingDto.getId());
        Room room = booking.getRoom();
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
    @ResponseBody
    public Boolean createBooking (Principal principal, @RequestBody BookingDto bookingDto){
        User manager = userService.getUserByEmail(principal.getName());
        Room room = roomService.findByManager(manager).get(0);
        Date dateLo = toDateAndTime(bookingDto.getStartTime());
        Date dateHi = toDateAndTime(bookingDto.getEndTime());

        bookingDto.setDateStartTime(dateLo);
        bookingDto.setDateEndTime(dateHi);

        Child child = childService.findById(bookingDto.getIdChild());

        // TODO: 10.06.2016  get booking by child
        if(roomService.isPeriodAvailable(dateLo, dateHi, room)){
            Booking booking = bookingDto.getBookingObject();
            booking.setRoom(room);
            booking.setChild(child);
            booking.setUser(child.getParentId());
            booking.setBookingState(BookingState.BOOKED);
            bookingDao.create(booking);
            bookingDao.update(booking);
            return true;
        }else {
            return false;
        }
    }


}
