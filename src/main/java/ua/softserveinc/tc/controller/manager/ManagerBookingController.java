package ua.softserveinc.tc.controller.manager;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.RoomService;

import static ua.softserveinc.tc.dto.ManagerBooking.BookingTimeDto;

@RestController
public class ManagerBookingController {

  @Autowired
  RoomService roomService;

  @Autowired
  BookingService bookingService;

  /**
   * Receives the date and id of room from the client. Figures out list all of the bookings
   * that have booking states BookingState.BOOKED and BookingState.Active for the given
   * date.
   * Sends to the client JSON with information about start time and end time for each booking.
   *
   * @param date date for which makes figuring out bookings
   * @param id id the room for which makes figuring out bookings
   * @return JSON with relevant information
   */
  @GetMapping("dailyNotCompletedBookings/{date}/{id}")
  public List<BookingTimeDto> dailyNotCompletedBookings(@PathVariable String date,
      @PathVariable Long id) {
    Room room = roomService.findByIdTransactional(id);
    Date startDate = toDateAndTime(date + " " + room.getWorkingHoursStart());
    Date endDate = toDateAndTime(date + " " + room.getWorkingHoursEnd());
    List<Booking> bookings = bookingService
        .getNotCompletedAndCancelledBookings(startDate, endDate, room);
    List<BookingTimeDto> result = bookings.stream().map(BookingTimeDto::new)
        .collect(Collectors.toList());
    return result;
  }
}