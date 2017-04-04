package ua.softserveinc.tc.controller.user;

import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;
import com.google.gson.Gson;
import javax.inject.Inject;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.softserveinc.tc.constants.ValidationConstants;
import ua.softserveinc.tc.constants.ErrorConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.util.Log;
import ua.softserveinc.tc.util.BookingsHolder;

import java.util.List;
import java.util.Locale;


/**
 * Controller for creating, reading, updating, cancelling, bookings.
 */
@Controller
public class CRUDBookingsController {

    @Log
    private static Logger log;

    @Inject
    private RoomService roomService;

    @Inject
    private BookingService bookingService;

    @Inject
    private MessageSource messageSource;

    /**
     * Receives the Id of room and user from GET http method and finds out all bookings with
     * status BOOKED for given room and user.
     *
     * If any of the input parameters is null then method returns ResponseEntity with "Bad Request"
     * http status (400). Otherwise returns the bookings dto objects in the body of object of
     * ResponseEntity with http status "OK" (200).
     *
     * @param idUser the given user
     * @param idRoom the given room
     * @param locale the given locale
     * @return ResponseEntity with appropriate http status and body that consists the dates
     * objects
     */
    @GetMapping(value = "/getallbookings/{idUser}/{idRoom}",
                produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> getAllBookings(@PathVariable Long idUser,
                                                 @PathVariable Long idRoom,
                                                 Locale locale) {

        ResponseEntity<String> resultResponse;

        if (idUser == null || idRoom == null) {
            resultResponse = getResponseEntity((Object)null, locale);

        } else {
            resultResponse = getResponseEntity(
                    bookingService.getAllBookingsByUserAndRoom(idUser, idRoom), locale);
        }

        return resultResponse;

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
    @GetMapping(value = "/disabled",  produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> getDisabledTimes(@RequestParam Long roomID, Locale locale) {

        ResponseEntity<String> resultResponse;

        if (roomID == null) {
            resultResponse = getResponseEntity((Object)null, locale);

        } else {
            resultResponse = getResponseEntity(roomService.getDisabledPeriods(roomID), locale);
        }

        return  resultResponse;
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
    @GetMapping(value = "/getRecurrentBookingForEditing/{recurrentId}",
                produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> getRecurrentBookingForEditing(@PathVariable Long recurrentId,
                                                                Locale locale) {

        BookingDto resultBookingDto = bookingService
                .getRecurrentBookingForEditingById(recurrentId);

        return getResponseEntity(resultBookingDto, locale);
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
    @PostMapping(value = "/makenewbooking", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> makeBooking(@RequestBody List<BookingDto> dtos, Locale locale) {
        /*System.out.println(dtos);
        Map<String,String> test = dtos.get(0);
        BookingDto dto = new BookingDto();
        dto.setUserId(Long.parseLong(test.get("userId")));
        dto.setRoomId(Long.parseLong(test.get("roomId")));
        dto.setKidId(Long.parseLong(test.get("kidId")));
        dto.setComment(test.get("comment"));
        dto.setStartTime(test.get("startTime"));
        dto.setEndTime(test.get("endTime"));
        List<BookingDto> list = new ArrayList<>();
        list.add(dto);
        //return null;*/
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
    @PostMapping(value = "/makerecurrentbookings", produces = "application/json; charset=UTF-8")
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
    @PostMapping(value = "/updatebooking", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateBooking(@RequestBody BookingDto dto, Locale locale) {

        return  getResponseEntity(bookingService.updateBooking(dto), locale);

    }

    /**
     * Receives the recurrent BookingDto object from POST http method and send them for updating.
     *
     * If any of the input parameters are not correct or the system failed to update all of the
     * bookings from the dto then the method returns ResponseEntity with "Bad Request" http
     * status (400). Otherwise returns list of the updated Bookings in the BookingsDto objects
     * in the body of object of ResponseEntity with http status "OK" (200).
     *
     * @param dto the recurrent BookingsDto object
     * @param locale the current request locale
     * @return ResponseEntity with appropriate http status and body that consists list of
     * the BookingsDto objects that represents updated bookings
     */
    @PostMapping(value = "/updaterecurrentbookings", produces = "application/json; charset=UTF-8")
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
    @GetMapping(value = "/cancelrecurrentbookings/{recurrentId}",
            produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> cancelRecurrentBookings(@PathVariable Long recurrentId,
                                                          Locale locale) {

        int numOfCancelledEntities = (recurrentId != null) ?
                bookingService.cancelBookingsByRecurrentId(recurrentId) : 0;


        return getResponseEntity(numOfCancelledEntities, locale);
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
    @GetMapping(value = "/cancelBooking/{idBooking}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> cancelBooking(@PathVariable Long idBooking, Locale locale) {

        int numOfCancelledEntities = (idBooking != null) ?
                bookingService.cancelBookingById(idBooking) : 0;

        return getResponseEntity(numOfCancelledEntities, locale);
    }

    /*
     * Receives a BookingsHolder that can contains a list of BookingDto objects
     * or Error code of string message.
     *
     * If a BookingsHolder contains aforementioned list
     * then method returns a ResponseEntity with status "Ok" (200) and the
     * given list in its body. If a BookingsHolder contains aforementioned error
     * message then method returns a ResponseEntity with status "Bad Request"
     * (400) and the given error message in its body. The given error message
     * is localized according to the user locale.
     *
     * @param resultTuple the given BookingsHolder
     * @param locale the given request locale
     * @return resulting ResponseEntity
     */
    private ResponseEntity<String> getResponseEntity(BookingsHolder resultTuple, Locale locale) {
        ResponseEntity<String> resultResponse;

        if (resultTuple.getBookings() == null) {
            resultResponse = getResponseEntity(false, resultTuple.getErrorCode(), locale);
        } else {
            resultResponse =
                    getResponseEntity(true, new Gson().toJson(resultTuple.getBookings()), locale);
        }

        return resultResponse;
    }

    /*
     * Creates and returns ResponseBody object according to a given response object. The
     * http status figure out from a given response. If response is not numeric object,
     * is not equal to null or if the response is a numeric value and is not equal or lesser 0
     * then http status is "OK" (200). If object is null or is a numeric value that equal or
     * lesser 0, then http status is "Bad Request" (400).
     *
     * If status is Ok then body of the resulting ResponseEntity is set to responseBody input
     * parameter without changes. Otherwise the common error message is set to responseBody
     * input parameter translated into language according to the given locale.
     *
     * RULES:
     *
     * BAD REQUEST:
     * - object is null
     * - numeric value equals or lesser then 0
     *
     * OK:
     * - in all other cases
     *
     * @param the given response object
     * @param locale the given request locale
     * @return the resulting ResponseEntity
     */
    private ResponseEntity<String> getResponseEntity (Object response, Locale locale) {

        ResponseEntity<String> resultResponse;

        //Check for null or numeric equal 0
        boolean okStatus = response != null
                && (!(response instanceof Number) || ((Number) response).doubleValue() > 0);

        if (okStatus) {
            resultResponse =
                    getResponseEntity(true, new Gson().toJson(response), locale);
        } else {
            resultResponse =
                    getResponseEntity(false, ValidationConstants.COMMON_ERROR_MESSAGE, locale);
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
            String responseMessage;

            try {
                responseMessage = messageSource.getMessage(responseBody, null, locale);
            } catch (NoSuchMessageException e) {
                log.error("The message wasn't found", e);
                responseMessage = ErrorConstants.PRIMARY_ERROR_MESSAGE;
            }
            resultResponse = ResponseEntity.badRequest()
                    .body(responseMessage);

        } else {
            resultResponse = ResponseEntity.ok()
                    .body(responseBody);
        }

        return resultResponse;
    }

}
