package ua.softserveinc.tc.controller.manager;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.ManagerBookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.validator.TimeValidatorImpl;

@RestController
public class ManagerBookingController {

  @Autowired
  RoomService roomService;

  @Autowired
  BookingService bookingService;

  @Autowired
  private TimeValidatorImpl timeValidator;


  /**
   * Receives the date, id of room and array of booking states from the client. Figures out list of
   * all booking that are in received states for the given date Send to the client information about
   * this bookings in JSON format
   *
   * @param date date for which makes figuring out bookings
   * @param id id the room for which makes figuring out bookings
   * @param state array of states that must be displayed
   * @return JSON with relevant information
   */
  @GetMapping(value = "dailyBookings/{date}/{id}/{state}")
  public List<ManagerBookingDTO> dailyBookingsByState(@PathVariable String date,
      @PathVariable Long id,
      @PathVariable BookingState[] state) {
    Room room = roomService.findByIdTransactional(id);
    Date dateLo = toDateAndTime(date + " " + room.getWorkingHoursStart());
    Date dateHi = toDateAndTime(date + " " + room.getWorkingHoursEnd());
    List<Booking> bookings = bookingService.getBookings(new Date[]{dateLo, dateHi}, room, state);

    List<ManagerBookingDTO> result = new ArrayList<>();
    for (Booking booking : bookings) {
      result.add(
          new ManagerBookingDTO.Builder().id(booking.getIdBook())
              .idChild(booking.getChild().getId())
              .kidName(booking.getChild().getFullName())
              .comment(booking.getComment())
              .startTime(booking.getBookingStartTime())
              .endTime(booking.getBookingEndTime())
              .durationBooking(booking.getDuration())
              .bookingState(booking.getBookingState())
              .build()
      );
    }
    Collections.sort(result, (r1, r2) -> {
      int cmp = r1.getBookingState().compareTo(r2.getBookingState());
      if (cmp == 0) {
        cmp = r1.getStartTime().compareTo(r2.getStartTime());
      }
      if (cmp == 0) {
        cmp = r1.getEndTime().compareTo(r2.getEndTime());
      }
      return cmp;
    });
    return result;
  }

  /**
   * Receives the date and id of room from the client. Figures out list all of the bookings that
   * have booking states BookingState.BOOKED and BookingState.Active for the given date. Sends to
   * the client JSON with information about start time and end time for each booking.
   *
   * @param date date for which makes figuring out bookings
   * @param id id the room for which makes figuring out bookings
   * @return JSON with relevant information
   */
  @GetMapping("dailyNotCompletedBookings/{date}/{id}")
  public List<ManagerBookingDTO> dailyNotCompletedBookings(@PathVariable String date,
      @PathVariable Long id) {
    Room room = roomService.findByIdTransactional(id);
    Date startDate = toDateAndTime(date + " " + room.getWorkingHoursStart());
    Date endDate = toDateAndTime(date + " " + room.getWorkingHoursEnd());
    List<Booking> bookings = bookingService
        .getNotCompletedAndCancelledBookings(startDate, endDate, room);
    List<ManagerBookingDTO> result = new ArrayList<>();
    for (Booking booking : bookings) {
      result.add(
          new ManagerBookingDTO.Builder().startTimeMillis(booking.getBookingStartTime())
              .endTimeMillis(booking.getBookingStartTime())
              .build());
    }
    return result;
  }


  /**
   * Receives PUT request from the client and PUT new start time to database for booking also
   * changes booking state to active
   *
   * @return void
   */
  @PutMapping("/setTime")
  public void setingBookingsStartTime(@RequestBody BookingDto bookingDto) {
    if (!timeValidator.isRoomTimeValid(bookingDto)) {
      return;
    }
    //TODO-VL Okay we should change this method to work without bookingDTO
    Booking booking = bookingService.confirmBookingStartTime(bookingDto);
    if (!booking.getBookingState().equals(BookingState.COMPLETED)) {
      booking.setBookingState(BookingState.ACTIVE);
    }
    bookingService.update(booking);
  }

  /**
   * Receives PUT request from the client and PUT new end time to database for booking also
   * changes booking state to COMPLETED
   *
   *
   * @return void
   */
  @PutMapping("/setEndTime")
  public void setingBookingsEndTime(@RequestBody BookingDto bookingDto) {
    //TODO-VL The same as setTime method
    Booking booking = bookingService.confirmBookingEndTime(bookingDto);
    booking.setBookingState(BookingState.COMPLETED);
    bookingService.update(booking);
  }

}
