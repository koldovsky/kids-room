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
import ua.softserveinc.tc.repo.BookingRepository;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.setTime;

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

     @RequestMapping(value = BookingConstants.Model.LIST_BOOKING, method = RequestMethod.GET)
     @ResponseBody
     public String listBookigs(Principal principal) {
         User manager = userService.getUserByEmail(principal.getName());
         Room room = manager.getRooms().get(0);
         Date dateLo = setTime(room.getWorkingHoursStart());
         Date dateHi = setTime(room.getWorkingHoursEnd());
         List<Booking> bookings =  bookingService.getBookings(dateLo, dateHi, room, BookingConstants.States.getNotCancelled());
         List<BookingDto> listBookingDto = new ArrayList<>();
         bookings.forEach(booking -> listBookingDto.add(new BookingDto(booking)));
         Gson gson = new Gson();
         return  gson.toJson(listBookingDto);
    }

     @RequestMapping(value = BookingConstants.Model.BOOK_DURATION, method = RequestMethod.POST, consumes = "application/json")
     @ResponseBody
     public String bookinkDuration(@RequestBody BookingDto bookingDto) throws ParseException{
         Booking booking = bookingService.findById(bookingDto.getId());
         Date date = bookingService.replaceBookingTime(booking, bookingDto.getEndTime());
         booking.setBookingEndTime(date);
         bookingService.calculateAndSetDuration(booking);
         BookingDto bookingDTOtoJson = new BookingDto(booking);
         Gson gson = new Gson();
         return  gson.toJson(bookingDTOtoJson);
    }

    @RequestMapping(value = "manager-daily-booking/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String bookingsByDay(@PathVariable Long id) {
        Room room = roomService.findById(id);
        Date dateLo = setTime(room.getWorkingHoursStart());
        Date dateHi = setTime(room.getWorkingHoursEnd());
        List<Booking> bookings = bookingService.getBookings(dateLo, dateHi, room, BookingConstants.States.getNotCancelled());
        Collections.sort(bookings, (b1, b2) -> b1.getBookingState().compareTo(b2.getBookingState()));
        Gson gson = new Gson();
        return gson.toJson(bookings.stream()
                .map(BookingDto::new)
                .collect(Collectors.toList()));
    }

    @ResponseBody
    @RequestMapping(value = "getAmountOfChildren/{roomId}", method = RequestMethod.GET)
    public Long getAmountOfChildrenInTheRoom(@PathVariable Long roomId) {
        Room room = roomService.findById(roomId);
        return bookingRepository.countByRoomAndBookingState(room, BookingState.ACTIVE);
    }
}

