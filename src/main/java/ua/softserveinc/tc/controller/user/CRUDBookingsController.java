package ua.softserveinc.tc.controller.user;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.TwoTuple;

import java.util.List;
import java.util.Collections;
import java.util.Locale;
import java.util.Date;


@RestController
public class CRUDBookingsController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "getallbookings/{idUser}/{idRoom}", produces = "text/plain; charset=UTF-8")
    public String getAllBookings(@PathVariable Long idUser, @PathVariable Long idRoom) {

        return new Gson().toJson(bookingService.getAllBookingsByUserAndRoom(idUser, idRoom));
    }

    /**
     * Receives the Id of room from GET http method and figures out all time periods where
     * given room is full i.e. there are no available places in the room.
     *
     * If the input parameter is null or is not corresponding to existed Id of room then method
     * returns ResponseEntity with "Bad Request" http status (400). Otherwise returns the
     * dates objects in the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param roomID the given room
     * @param locale the given locale
     * @return ResponseEntity with appropriate http status and body that consists the dates
     * objects
     */
    @GetMapping(value = "/disabled",  produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> getDisabledTime(@RequestParam Long roomID, Locale locale) {
        ResponseEntity<String> resultResponse;

        List<Date[]> disabledDates = (roomID != null) ?
                roomService.getDisabledPeriods(roomID) : Collections.emptyList();

        if(!disabledDates.isEmpty()) {
            resultResponse =
                    getResponseEntity(true, new Gson().toJson(disabledDates), locale);
        } else {
            resultResponse =
                    getResponseEntity(false, ValidationConstants.COMMON_ERROR_MESSAGE, locale);
        }

        return resultResponse;
    }

    /**
     * Receives the recurrent Id from GET http method and create BookingDto object that contains
     * start and end date for recurrent period of time, and weekdays arrays.
     *
     * If the input parameter is null or is not corresponding to existed recurrent Id then method
     * returns ResponseEntity with "Bad Request" http status (400). Otherwise returns the
     * BookingsDto object in the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param recurrentId the given recurrent Id
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists the BookingsDto
     * object
     */
    @GetMapping(value = "getRecurrentBookingForEditing/{recurrentId}",
                produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> getRecurrentBookingForEditing(@PathVariable Long recurrentId,
                                                                Locale locale) {
        ResponseEntity<String> resultResponse;

        BookingDto resultBookingDto = bookingService
                .getRecurrentBookingForEditingById(recurrentId);

        if(resultBookingDto != null) {
            resultResponse = getResponseEntity(true, new Gson().toJson(resultBookingDto), locale);
        } else {
            resultResponse =
                    getResponseEntity(false, ValidationConstants.COMMON_ERROR_MESSAGE, locale);
        }

        return resultResponse;
    }

    /**
     * Receives the list of BookingDto objects from POST http method and send them for persisting.
     *
     * If any of the input parameters are not correct or the system failed to persist all of the
     * bookings from the dto then method returns ResponseEntity with "Bad Request" http status
     * (400). Otherwise returns list of the persisted Bookings in the BookingsDto objects in the
     * body of object of ResponseEntity with http status "OK" (200).
     *
     * @param dtos list of BookingsDto objects
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents persisted bookings
     */
    @PostMapping(value = "makenewbooking", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> makeBooking(@RequestBody List<BookingDto> dtos, Locale locale) {

        return  getResponseEntity(bookingService.makeBookings(dtos), locale);
    }

    /**
     * Receives the list of recurrent BookingDto objects from POST http method and send them for
     * persisting.
     *
     * If any of the input parameters are not correct or the system failed to persist
     * all of the bookings from the dto then method returns ResponseEntity with "Bad Request" http
     * status (400). Otherwise returns list of the persisted Bookings in the BookingsDto objects in
     * the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param dtos list of BookingsDto objects
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents persisted bookings
     */
    @PostMapping(value = "makerecurrentbookings", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> makeRecurrentBookings(@RequestBody List<BookingDto> dtos,
                                                        Locale locale) {

        return  getResponseEntity(bookingService.makeRecurrentBookings(dtos), locale);
    }

    /**
     * Receives the BookingDto object from POST http method and send them for updating.
     *
     * If any of the input parameters are not correct or the system failed to update all of the
     * bookings from the dto then the method returns ResponseEntity with "Bad Request" http
     * status (400). Otherwise returns list of the updated Bookings in the BookingsDto objects
     * in the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param dto the BookingsDto object
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents updated bookings
     */
    @PostMapping(value = "updaterecurrentbookings", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateRecurrentBookings(@RequestBody BookingDto dto,
                                                          Locale locale) {

        return  getResponseEntity(bookingService.updateRecurrentBookings(dto), locale);
    }

    /**
     * Receives the recurrent id of bookings from GET http method and send them for cancelling.
     *
     * If input parameter is null or number of cancelled bookings is 0 then the method returns
     * ResponseEntity with "Bad Request" http status (400) and this number. Otherwise returns
     * number of the cancelled Bookings in the body of object of ResponseEntity with http status
     * "OK" (200).
     *
     * @param recurrentId the recurrent id of bookings
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists number of
     * cancelled bookings
     */
    @GetMapping(value = "cancelrecurrentbookings/{recurrentId}",
            produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> cancelRecurrentBookings(@PathVariable Long recurrentId,
                                                          Locale locale) {
        ResponseEntity<String> resultResponse;

        int numOfCancelledEntities = (recurrentId != null) ?
                bookingService.cancelBookingsByRecurrentId(recurrentId) : 0;

        if(numOfCancelledEntities != 0) {
            resultResponse =
                    getResponseEntity(true, new Gson().toJson(numOfCancelledEntities), locale);
        } else {
            resultResponse =
                    getResponseEntity(false, ValidationConstants.COMMON_ERROR_MESSAGE, locale);
        }

        return resultResponse;
    }

    /**
     * Receives the id of booking from GET http method and send it for cancelling.
     *
     * If input parameter is null or represent not existed booking then the method returns
     * ResponseEntity with "Bad Request" http status (400) and number 0. Otherwise returns
     * 1 in the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param idBooking the given booking id
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists number of
     * cancelled bookings
     */
    @GetMapping(value = "cancelBooking/{idBooking}", produces = "text/plain; charset=UTF-8")
    public ResponseEntity<String> cancelBooking(@PathVariable Long idBooking, Locale locale) {
        ResponseEntity<String> resultResponse;

        int numOfCancelledEntities = (idBooking != null) ?
                bookingService.cancelBookingById(idBooking) : 0;

        if(numOfCancelledEntities != 0) {
            resultResponse =
                    getResponseEntity(true, new Gson().toJson(numOfCancelledEntities), locale);
        } else {
            resultResponse =
                    getResponseEntity(false, ValidationConstants.COMMON_ERROR_MESSAGE, locale);
        }

        return resultResponse;
    }

    /*
     * Receives a TwoTuple that can contains a list of BookingDto objects
     * or Error string message.
     *
     * If a TwoTuple contains aforementioned list
     * then method returns a ResponseEntity with status "Ok" (200) and the
     * given list in its body. If a TwoTuple contains aforementioned error
     * message then method returns a ResponseEntity with status "Bad Request"
     * (400) and the given error message in its body. The given error message
     * is localized according to the user locale.
     *
     * @param resultTuple the given TwoTuple
     * @param locale the given request locale
     * @return resulting ResponseEntity
     */
    private ResponseEntity<String> getResponseEntity(TwoTuple<List<BookingDto>,String> resultTuple,
                                                     Locale locale) {
        ResponseEntity<String> resultResponse;

        if (resultTuple.getFirst() == null) {
            resultResponse = getResponseEntity(false, resultTuple.getSecond(), locale);
        } else {
            resultResponse =
                    getResponseEntity(true, new Gson().toJson(resultTuple.getFirst()), locale);
        }

        return resultResponse;
    }

    /*
     * Creates and returns ResponseBody object according to given httpStatus and response
     * body.
     *
     * If the httpStatus is false than it is interpreted as "Bad Request" http status
     * (400), otherwise - as http status "OK" (200). If status is Ok then body of the resulting
     * ResponseEntity is set to responseBody input parameter without changes. Otherwise the
     * given parameter is interpreted as error code and is translated into language according
     * to locale.
     *
     * @param the httpStatus the given http status
     * @param the responseBody the JSON object, or error code for localization
     * @param locale the given request locale
     * @return the resulting ResponseEntity
     */
    private ResponseEntity<String> getResponseEntity(boolean httpStatus, String responseBody,
                                                     Locale locale) {
        ResponseEntity<String> resultResponse;


        if (!httpStatus) {
            resultResponse = ResponseEntity.badRequest()
                    .body(messageSource.getMessage(responseBody, null, locale));

        } else {
            resultResponse = ResponseEntity.ok()
                    .body(responseBody);
        }

        return resultResponse;
    }
}
