package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;

public class BookingUtil {

    public static final int BOOKING_START_HOUR = 0;
    public static final int BOOKING_START_MINUTE = 0;
    public static final int BOOKING_START_SECOND = 0;
    public static final int BOOKING_END_HOUR = 23;
    public static final int BOOKING_END_MINUTE = 59;
    public static final int BOOKING_END_SECOND = 59;

    private BookingUtil(){}

    public static boolean checkBookingTimeOverlap(BookingDto checked, Booking booked) {
        return (!(!(DateUtil.toDateISOFormat(checked.getEndTime())
                .after(booked.getBookingStartTime()))
                || !(DateUtil.toDateISOFormat(checked.getStartTime())
                .before(booked.getBookingEndTime()))
        ) && checked.getKidId().equals(booked.getChild().getId()));
    }
}
