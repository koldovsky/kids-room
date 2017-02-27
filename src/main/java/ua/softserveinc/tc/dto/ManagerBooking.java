package ua.softserveinc.tc.dto;

import java.io.Serializable;
import ua.softserveinc.tc.entity.Booking;

public final class ManagerBooking {

  private ManagerBooking() {

  }

  public static class BookingTimeDto implements Serializable {

    private Long startTimeMillis;
    private Long endTimeMillis;

    public BookingTimeDto(Booking booking) {
      startTimeMillis = booking.getBookingStartTime().getTime();
      endTimeMillis = booking.getBookingEndTime().getTime();
    }

    public Long getStartTimeMillis() {
      return startTimeMillis;
    }

    public void setStartTimeMillis(Long startTimeMillis) {
      this.startTimeMillis = startTimeMillis;
    }

    public Long getEndTimeMillis() {
      return endTimeMillis;
    }

    public void setEndTimeMillis(Long endTimeMillis) {
      this.endTimeMillis = endTimeMillis;
    }
  }
}
