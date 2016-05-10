package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;

import java.util.Date;
import java.util.List;

/**
 * Created by TARAS on 01.05.2016.
 */
public interface BookingService extends BaseService<Booking>
{
    List<Booking> getBookingsOfThisDay();
    List<Booking> getBookingsByUser(User user);
    List<Booking> getBookingsByRangeOfTime(String startDate, String endDate);
}
