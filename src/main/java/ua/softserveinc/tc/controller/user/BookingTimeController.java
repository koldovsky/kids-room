package ua.softserveinc.tc.controller.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import ua.softserveinc.tc.constants.LocaleConstants;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.JsonUtil;
import ua.softserveinc.tc.util.TwoTuple;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Calendar;
import java.util.Locale;

/*
 * Rewritten by Sviatoslav Hryb on 11-Jan-2017
 */
@RestController
public class BookingTimeController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(value = "getallbookings/{idUser}/{idRoom}",
            produces = "text/plain; charset=UTF-8")
    public String getAllBookings(@PathVariable Long idUser,
                                 @PathVariable Long idRoom) {
        return new Gson().toJson(bookingService.getAllBookingsByUserAndRoom(idUser, idRoom));
    }

    @GetMapping("/disabled")
    public String getDisabledTime(@RequestParam Long roomID) {
        Room room = roomService.findByIdTransactional(roomID);

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
     * Receives the recurrent Id from GET http method and create BookingDto object that contains
     * start and end date for recurrent period of time, and weekdays arrays.
     * If the input parameter is null or is not corresponding to existed recurrent Id then method
     * returns ResponseEntity with "Bad Request" http status (400). Otherwise returns the BookingsDto
     * object in the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param recurrentId the given recurrent Id
     * @return ResponseEntity with appropriate http status and body that consists the BookingsDto object
     */
    @GetMapping(value = "getRecurrentBookingForEditing/{recurrentId}",
                produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> getRecurrentBookingForEditing(@PathVariable Long recurrentId) {
        ResponseEntity<String> resultResponse;
        Locale locale = (Locale) request.getSession()
                .getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);
        locale = (locale == null) ? request.getLocale() : locale;

        BookingDto resultBookingDto = bookingService
                .getRecurrentBookingForEditingById(recurrentId);

        if(resultBookingDto != null) {
            resultResponse = ResponseEntity.status(HttpStatus.OK)
                    .body(new Gson().toJson(resultBookingDto));
        } else {
            resultResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(messageSource.getMessage(
                            ValidationConstants.VALIDATION_NOT_CORRECT_USAGE, null, locale));
        }

        return resultResponse;
    }

    /**
     * Receives the list of BookingDto objects from POST http method and send them for persisting.
     * If any of the input parameters are not correct or the system failed to persist all of the
     * bookings from the dto then method returns ResponseEntity with "Bad Request" http status (400).
     * Otherwise returns list of the persisted Bookings in the BookingsDto objects in the body
     * of object of ResponseEntity with http status "OK" (200).
     *
     * @param dtos list of BookingsDto objects
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents persisted bookings
     */
    @PostMapping(value = "makenewbooking", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> makeBooking(@RequestBody List<BookingDto> dtos) {

        return  getResponseEntity(bookingService.makeBookings(dtos));
    }

    /**
     * Receives the list of recurrent BookingDto objects from POST http method and send them for
     * persisting. If any of the input parameters are not correct or the system failed to persist
     * all of the bookings from the dto then method returns ResponseEntity with "Bad Request" http
     * status (400). Otherwise returns list of the persisted Bookings in the BookingsDto objects in
     * the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param dtos list of BookingsDto objects
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents persisted bookings
     */
    @PostMapping(value = "makerecurrentbookings", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> makeRecurrentBookings(@RequestBody List<BookingDto> dtos) {

        return  getResponseEntity(bookingService.makeRecurrentBookings(dtos));
    }

    /**
     * Receives the BookingDto object from POST http method and send them for updating. If any of the
     * input parameters are not correct or the system failed to update all of the bookings from the dto
     * then the method returns ResponseEntity with "Bad Request" http status (400). Otherwise returns
     * list of the updated Bookings in the BookingsDto objects in the body of object of ResponseEntity
     * with http status "OK" (200).
     *
     * @param dto the BookingsDto object
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents updated bookings
     */
    @PostMapping(value = "updaterecurrentbookings", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateRecurrentBookings(@RequestBody BookingDto dto) {

        return  getResponseEntity(bookingService.updateRecurrentBookings(dto));
    }

    /*
     * Receives a TwoTuple that can contains a list of BookingDto objects
     * or Error string message. If a TwoTuple contains aforementioned list
     * then method returns a ResponseEntity with status "Ok" (200) and the
     * given list in its body. If a TwoTuple contains aforementioned error
     * message then method returns a ResponseEntity with status "Bad Request"
     * (400) and the given error message in its body. The given error message
     * is localized according to the user locale.
     *
     * @param resultTuple the given TwoTuple
     * @return resulting ResponseEntity
     */
    private ResponseEntity<String> getResponseEntity(TwoTuple<List<BookingDto>, String> resultTuple) {
        ResponseEntity<String> resultResponse;
        Locale locale = (Locale) request.getSession()
                .getAttribute(LocaleConstants.SESSION_LOCALE_ATTRIBUTE);
        locale = (locale == null) ? request.getLocale() : locale;

        if (resultTuple.getFirst() == null) {
            resultResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(messageSource.getMessage(resultTuple.getSecond(), null, locale));

        } else {
            resultResponse = ResponseEntity.status(HttpStatus.OK)
                    .body(new Gson().toJson(resultTuple.getFirst()));
        }

        return resultResponse;
    }
}
