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
import java.util.*;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

/**
 * Created by Петришак on 04.06.2016.
 */

@Controller
public class BookingEditController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ChildService childService;

    @Autowired
    private BookingDao bookingDao;

    @RequestMapping(value = BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW)
    public ModelAndView editBookingModel (Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_EDIT_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> rooms = currentManager.getRooms();
        List<User> users = userService.findAllUsersByRole(Role.USER);
        Collections.sort(users, (user1, user2)->user1.getFirstName().compareTo(user2.getFirstName()));
        model.addAttribute("rooms", rooms);
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

    @RequestMapping(value = BookingConstants.Model.SET_START_TIME, method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String setingBookingsStartTime(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.confirmBookingStartTime(bookingDto);
        if(!(booking.getBookingState()==BookingState.COMPLETED)){
            booking.setBookingState(BookingState.ACTIVE);
        }
        bookingService.update(booking);
        BookingDto bookingDTOtoJson = new BookingDto(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTOtoJson);
    }

    @RequestMapping(value = BookingConstants.Model.SET_END_TIME, method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String setingBookingsEndTime(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.confirmBookingEndTime(bookingDto);
        booking.setBookingState(BookingState.COMPLETED);
        bookingService.update(booking);
        BookingDto bookingDTOtoJson = new BookingDto(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDTOtoJson);
    }

    @RequestMapping (value = "dailyBookings/{date}/{id}/{state}",
            method = RequestMethod.GET)
    @ResponseBody
    public String dailyBookingsByState(@PathVariable String date,
                                      @PathVariable Long id,
                                      @PathVariable BookingState[] state){
        Room room = roomService.findById(id);
        Date dateLo = toDateAndTime(date + " " +room.getWorkingHoursStart());
        Date dateHi = toDateAndTime(date + " " +room.getWorkingHoursEnd());
        List<Booking> bookings = bookingService.getBookings(dateLo, dateHi, room, state);
        if (Arrays.equals(state, BookingConstants.States.getNotCancelled())) {
            Collections.sort(bookings, (b1, b2) -> b1.getBookingState().compareTo(b2.getBookingState()));
        }
        Gson gson = new Gson();
        return  gson.toJson(bookings.stream()
                .map(BookingDto::new)
                .collect(Collectors.toList()));
    }

    @RequestMapping(value = "change-booking", method = RequestMethod.POST,
            consumes = "application/json")
    @ResponseBody
    public Boolean isPossableUpdate(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.findById(bookingDto.getId());
        Date startTime = toDateAndTime(bookingDto.getStartTime());
        Date endTime = toDateAndTime(bookingDto.getEndTime());
        if(roomService.isPossibleUpdate(bookingDto)){
            booking.setBookingEndTime(endTime);
            booking.setBookingStartTime(startTime);
            booking.setComment(bookingDto.getComment());
            bookingService.update(booking);
            return true;
        }
        return false;
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
