package ua.softserveinc.tc.controller.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dao.UserDao;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.server.exception.DuplicateBookingException;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.ChildService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.JsonUtil;
import ua.softserveinc.tc.validator.BookingValidator;
import ua.softserveinc.tc.validator.TimeValidator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


@RestController
public class BookingTimeController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ChildService childService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TimeValidator timeValidator;

    @Autowired
    private BookingValidator bookingValidator;


    @PostMapping("getroomproperty")
    public String getRoomProperty(@RequestBody Integer roomId) {

        return null;
    }

    @PostMapping(value ="makenewbooking", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> getBooking(@RequestBody List<BookingDto> dtos,
                                             BindingResult bindingResult) {
        if (bookingService.hasDuplicateBookings(dtos)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ValidationConstants.DUPLICATE_BOOKINGS_MESSAGE);
        }

        for (BookingDto dto : dtos) {
            if (!this.timeValidator.validateBooking(dto)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        ValidationConstants.END_TIME_BEFORE_START_TIME);
            }
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

        List<BookingDto> dto = bookingService.persistBookingsFromDto(dtos);

        if (dto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    ValidationConstants.ROOM_IS_FULL_MESSAGE);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new Gson().toJson(dto));


    }

    @GetMapping(value = "getallbookings/{idUser}/{idRoom}",
                produces = "text/plain;charset=UTF-8")
    public String getAllBookings(@PathVariable Long idUser,
                                 @PathVariable Long idRoom) {
        return new Gson().toJson(bookingService.getAllBookingsByUserAndRoom(idUser, idRoom));
    }

    @GetMapping("/disabled")
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
        Map<String, String> blockedPeriods = roomService.getBlockedPeriods(room, start, end);
        return JsonUtil.toJson(blockedPeriods);
    }

    /**
     * Receives the list of BookingDto objects from POST http method. Then validates the
     * input parameter for correctness. If any of the input parameters are not correct or
     * the system failed to persist all of the bookings from the dto then method returns
     * ResponseEntity with "Bad Request" http status (400). Otherwise returns list of the
     * persisted Bookings in the BookingsDto objects in the body of object of ResponseEntity
     * with http status "OK" (200).
     *
     * @param bookingDtos list of BookingsDto objects
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents persisted bookings
     */
    @PostMapping(value = "makerecurrentbookings", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> makeRecurrentBookings(@RequestBody List<BookingDto> bookingDtos) {
        return bookingService.makeRecurrentBookings(bookingDtos);
    }

    @GetMapping(value = "getRecurrentBookingForEditing/{recurrentId}",
                produces = "text/plain;charset=UTF-8")
    public String getRecurrentBookingForEditing(@PathVariable  Long recurrentId) {
        return JsonUtil.toJson(bookingService.getRecurrentBookingForEditingById(recurrentId));
    }



    @PostMapping("updaterecurrentbookings")
    public ResponseEntity<String>  updateRecurrentBookingsCtrl(@RequestBody BookingDto recurrentBookingDto,
                                                               BindingResult bindingResult) {
        bookingValidator.validate(recurrentBookingDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldError().getCode());
        }
        List<BookingDto> bookings = new ArrayList<>();
        try {
            bookings = bookingService.updateRecurrentBookings(recurrentBookingDto);
        }catch (DuplicateBookingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ValidationConstants.DUPLICATE_BOOKINGS_MESSAGE);
        }
        if (bookings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ValidationConstants.NO_DAYS_FOR_BOOKING);
        }

        return ResponseEntity.status(HttpStatus.OK).body(JsonUtil.toJson(bookings));
    }

}
