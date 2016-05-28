package ua.softserveinc.tc.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.service.BookingService;

import java.util.List;

/**
 * Created by Demian on 28.05.2016.
 */
public class SumCalculation implements Job
{
    @Autowired
    private BookingService bookingService;

    public void execute(JobExecutionContext context)
    {
        List<Booking> bookings = bookingService.getBookingsWithZeroSum();
        bookings.forEach(booking -> bookingService.calculateAndSetSum(booking));
    }
}
