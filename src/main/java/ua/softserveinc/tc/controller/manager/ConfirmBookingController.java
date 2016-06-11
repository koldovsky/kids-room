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
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;

import java.security.Principal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.util.DateUtil.*;

/**
 * Created by Петришак on 08.05.2016.
 */
@Controller
public class ConfirmBookingController {
    @Autowired
    BookingDao bookingDao;
    @Autowired
    ChildService child;
    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;
    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW)
    public ModelAndView listBookings(Model model, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(BookingConstants.Model.MANAGER_CONF_BOOKING_VIEW);
        User currentManager = userService.getUserByEmail(principal.getName());
        List<Room> rooms = roomService.findByManger(currentManager);
        List<Booking> bookings =  bookingService.getBookings(workingHours().get(0), workingHours().get(1), rooms.get(0), BookingConstants.States.NOT_CANCELLED);
        model.addAttribute(BookingConstants.Model.LIST_BOOKINGS, bookings);
        model.addAttribute("rooms", rooms);
        return modelAndView;
    }


    @RequestMapping(value = BookingConstants.Model.CANCEL_BOOKING, method = RequestMethod.GET)
    @ResponseBody
    public String cancelBooking (@PathVariable Long idBooking) {
        Booking booking = bookingService.findById(idBooking);
        booking.setBookingState(BookingState.CANCELLED);
        booking.setSum(0L);
        bookingService.update(booking);
        BookingDto bookingDto = new BookingDto(booking);
        Gson gson = new Gson();
        return  gson.toJson(bookingDto);
    }

    @RequestMapping(value = BookingConstants.Model.SET_START_TIME, method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String setingBookingsStartTime(@RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.confirmBookingStartTime(bookingDto);
        booking.setBookingState(BookingState.ACTIVE);
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
         User currentManager = userService.getUserByEmail(principal.getName());
         Room currentRoom = roomService.findByManger(currentManager).get(0);
         List<Booking> bookings =  bookingService.getBookings(workingHours().get(0), workingHours().get(1),currentRoom, BookingConstants.States.NOT_CANCELLED);
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

    @RequestMapping (value = "manager-dayle-booking/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public String bookingsByDay(@PathVariable Long id){
        Date toDay= new Date();
        Room room = roomService.findById(id);
        List<Booking> bookings = bookingService.getBookings(setStartTime(toDay), setEndTime(toDay), room, BookingConstants.States.NOT_CANCELLED);
        Gson gson = new Gson();
        return  gson.toJson(bookings.stream()
                .map(BookingDto::new)
                .collect(Collectors.toList()));
    }

}
