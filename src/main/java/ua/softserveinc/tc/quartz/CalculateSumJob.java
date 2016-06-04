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
    BookingService bookingService;

    public void task()
    {
        List<Booking> bookings = bookingService.getBookingsWithZeroSum();
        System.out.println("Quartz is running, list of bookings is null: "
                + (bookings == null) + ", size of the list: " + bookings.size());
        bookings.forEach(bookingService::calculateAndSetSum);
    }
}
