package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;

import java.util.List;

/**
 * Created by Demian on 03.06.2016.
 */
@Service("calculateSum")
public class CalculateSumJob
{
    @Autowired
    private BookingService bookingService;

    private void task()
    {
        List<Booking> bookings = bookingService.getBookingsWithZeroSum();
        bookings.forEach(bookingService::calculateAndSetSum);
    }
}
