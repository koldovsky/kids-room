package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.impl.BookingServiceImpl;
import ua.softserveinc.tc.util.DateUtil;

import java.util.List;

@Service(QuartzConstants.CALCULATE_SUM)
public class CalculateSumJob {

    @Autowired
    private BookingService bookingService;

    private void task() {
        List<Booking> bookings = bookingService.findByBookingState(BookingState.CALCULATE_SUM);
        bookings.forEach(booking -> bookingService.calculateAndSetSum(booking,
                DateUtil.dateToLocalTime(booking.getBookingStartTime())));
    }
}