package ua.softserveinc.tc.controller.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.JsonUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Map;


@Controller
public class BookingTimeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;


    @RequestMapping(value = "getroomproperty", method = RequestMethod.POST)
    @ResponseBody
    public String getRoomProperty(@RequestBody Integer roomId) {

        return null;
    }

    @RequestMapping(value = "makenewbooking", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> getBooking(@RequestBody List<BookingDto> dtos) {
        if (bookingService.checkForDuplicateBooking(dtos)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationConstants.DUPLICATE_BOOKING_MESSAGE);
        }

        dtos.forEach(dto -> {
            dto.setUser(userDao.findById(dto.getUserId()));
            dto.setChild(childService.findById(dto.getKidId()));
            dto.setRoom(roomService.findById(dto.getRoomId()));
            dto.setBookingState(BookingState.BOOKED);
            dto.setKidName(childService.findById(dto.getKidId()).getFullName());
            dto.setDateStartTime(DateUtil.toDateISOFormat(dto.getStartTime()));
            dto.setDateEndTime(DateUtil.toDateISOFormat(dto.getEndTime()));
        });

        List<BookingDto> dto = bookingService.persistBookingsFromDtoAndSetId(dtos);

        if (dto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ValidationConstants.ROOM_IS_FULL_MESSAGE);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(dto));


    }

    @RequestMapping(value = "getallbookings/{idUser}/{idRoom}",
            method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllBookings(@PathVariable Long idUser,
                                 @PathVariable Long idRoom) {
        return new Gson().toJson(bookingService.getAllBookingsByUserAndRoom(idUser, idRoom));
    }

    @RequestMapping(value = "/disabled", method = RequestMethod.GET)
    @ResponseBody
    public String getDisabledTime(@RequestParam Long roomID) {
        Room room = roomService.findById(roomID);

        Calendar start = Calendar.getInstance();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.HOUR_OF_DAY, 0);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        end.add(Calendar.MONTH, 1);
        Map<String, String> mp = roomService.getBlockedPeriods(room, start, end);
        return JsonUtil.toJson(mp);
    }

    @RequestMapping(value = "getrecurrentbookings", method = RequestMethod.POST)
    @ResponseBody
    public String makeRecurrentBookings(@RequestBody List<BookingDto> bookingDtos) {
        for (BookingDto bookingDto : bookingDtos) {
            if (bookingDto.getIdChild() == null) {
                bookingDto.setIdChild(bookingDto.getKidId());
            }

            if (bookingDto.getKidId() == null) {
                bookingDto.setKidId(bookingDto.getIdChild());
            }
        }
        List<BookingDto> bookings = bookingService.makeRecurrentBookings(bookingDtos);

        return new Gson().toJson(bookings);
    }


    @RequestMapping(value = "getRecurrentBookingForEditing/{recurrentId}",
            method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getRecurrentBookingForEditing(@PathVariable  Long recurrentId) {
        return new Gson().toJson(bookingService.getRecurrentBookingForEditingById(recurrentId));
    }



}
