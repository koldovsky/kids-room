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
import ua.softserveinc.tc.dto.ChildDto;
import ua.softserveinc.tc.entity.*;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    ChildService childService;

    @Autowired
    BookingDao bookingDao;

    @RequestMapping(value = BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW)
    public ModelAndView editBookingModel (Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> listRoom = currentManager.getRooms();
        List<User> users = userService.findAllUsersByRole(Role.USER);
        model.addAttribute("rooms", listRoom);
        model.addAttribute("users", users);
        return modelAndView;
    }
    @RequestMapping(value = BookingConstants.Model.CANCEL_BOOKING, method = RequestMethod.GET)
    @ResponseBody
    public String cancelBooking (@PathVariable Long idBooking) {
        Booking booking = bookingService.findById(idBooking);
        booking.setBookingState(BookingState.CANCELLED);
        booking.setSum(0L);
        booking.setDuration(0L);
        bookingService.update(booking);
        BookingDto bookingDto = new BookingDto(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDto);
    }

    @RequestMapping (value = "manager-edit-booking/{date}/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public String bookingsByDay(@PathVariable String date,
                                @PathVariable Long id){
        Room room = roomService.findById(id);
        Date dateLo = toDateAndTime(date + " " +room.getWorkingHoursStart());
        Date dateHi = toDateAndTime(date + " " +room.getWorkingHoursEnd());
        List<Booking> bookings = bookingService.getBookings(dateLo, dateHi, room, BookingConstants.States.getNotCancelled());
        Collections.sort(bookings, (b1, b2) -> b1.getBookingState().compareTo(b2.getBookingState()));
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
        System.out.println(startTime);
        System.out.println(startTime);
        System.out.println(startTime);
        System.out.println(startTime);
        System.out.println(startTime);
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(endTime);
        System.out.println(endTime);
        System.out.println(endTime);
        System.out.println(endTime);
        System.out.println(endTime);
        System.out.println(endTime);
        if(roomService.isPeriodAvailable(startTime, endTime, room)) {
            booking.setBookingEndTime(endTime);
            booking.setBookingStartTime(startTime);
            booking.setComment(bookingDto.getComment());
            bookingService.update(booking);
            return true;
        }
        return false;
    }
    @RequestMapping(value = "create-booking", method = RequestMethod.POST,  consumes = "application/json")
    @ResponseBody
    public Boolean createBooking (Principal principal, @RequestBody BookingDto bookingDto){
        User manager = userService.getUserByEmail(principal.getName());
        Room room = manager.getRooms().get(0);
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
    @RequestMapping(value = "get-kids/{id}")
    @ResponseBody
    public String listKids (@PathVariable Long id){
        Set<Child> kids = userService.findById(id).getChildren();
        Gson gson = new Gson();
        return  gson.toJson(kids.stream()
                .map(ChildDto::new)
                .collect(Collectors.toList()));
    }
}
