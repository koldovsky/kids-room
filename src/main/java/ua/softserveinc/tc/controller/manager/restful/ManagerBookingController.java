package ua.softserveinc.tc.controller.manager.restful;

import static ua.softserveinc.tc.util.DateUtil.toDateAndTime;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.dto.ChildDto;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.dto.ManagerBookingDTO;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.DayDiscountService;
import ua.softserveinc.tc.service.RoomService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.validator.TimeValidatorImpl;

@RestController
@RequestMapping("/restful/manager-booking/")
public class ManagerBookingController {

  @Autowired
  RoomService roomService;

  @Autowired
  BookingService bookingService;

  @Autowired
  private TimeValidatorImpl timeValidator;

  @Autowired
  private UserService userService;

  @Autowired
  private DayDiscountService dayDiscountService;

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
  @GetMapping(value = "{date}/{id}/{state}")
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
          new ManagerBookingDTO.Builder().withId(booking.getIdBook())
              .withIdChild(booking.getChild().getId())
              .withKidName(booking.getChild().getFullName())
              .withComment(booking.getComment())
              .withStartTime(booking.getBookingStartTime())
              .withEndTime(booking.getBookingEndTime())
              .withDurationBooking(booking.getDuration())
              .withBookingState(booking.getBookingState())
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
  @GetMapping("/{date}/{id}")
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
          new ManagerBookingDTO.Builder().withStartTimeMillis(booking.getBookingStartTime())
              .withEndTimeMillis(booking.getBookingStartTime())
              .build());
    }
    return result;
  }

  /**
   * Counting number of children in the selected room for appropriate date.
   *
   * @param date current date for counting active children
   * @param id selected room id
   * @return number of active children
   */
  @GetMapping("amountOfKids/{date}/{id}")
  public Long getAmountOfChildrenForCurrentDay(@PathVariable String date,
      @PathVariable Long id) {
    Room room = roomService.findByIdTransactional(id);
    Date dateLo = toDateAndTime(date + " " + room.getWorkingHoursStart());
    Date dateHi = toDateAndTime(date + " " + room.getWorkingHoursEnd());
    List<Booking> bookings = bookingService
        .getBookings(new Date[]{dateLo, dateHi}, room, BookingState.ACTIVE);

    return (bookings.size() != 0) ? bookings.size() : 0L;
  }

  /**
   * Receives GET request with id of parent and sends to client information about all kids
   * for this parent
   *
   * @param id selected parent id
   * @return List of parent kids
   */
  @GetMapping("/{id}")
  public List<ChildDto> listKids(@PathVariable Long id) {
    List<Child> kids = userService.getEnabledChildren(userService.findByIdTransactional(id));
    return kids.stream()
        .map(ChildDto::new)
        .collect(Collectors.toList());
  }

  /**
   * Receives PUT request from the client and PUT new start time to database for booking also
   * changes booking state to active
   *
   * @return void
   */
  @PutMapping("/startTime")
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
  @PutMapping("/endTime")
  public void setingBookingsEndTime(@RequestBody BookingDto bookingDto) {
    //TODO-VL The same as setTime method
    Booking booking = bookingService.confirmBookingEndTime(bookingDto);
    booking.setBookingState(BookingState.COMPLETED);
    bookingService.update(booking);
  }

  @GetMapping("/discount/{startDate}/{endDate}/{startTime}/{endTime}")
  public List<DayDiscountDTO> getDiscountsForCurrentPeriod(@PathVariable String startDate,
                                                           @PathVariable String endDate,
                                                           @PathVariable String startTime,
                                                           @PathVariable String endTime) {

    return dayDiscountService.getDayDiscountsForPeriod(LocalDate.parse(startDate), LocalDate.parse(endDate),
            LocalTime.parse(startTime), LocalTime.parse(endTime));
  }

}