package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.repo.BookingRepository;
import ua.softserveinc.tc.service.BookingService;

import java.util.List;

/**
 * Created by Demian on 03.06.2016.
 */
@Service(QuartzConstants.CALCULATE_SUM)
public class CalculateSumJob {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepository bookingRepository;

    private void task() {
        List<Booking> bookings = bookingRepository.findByBookingState(BookingState.CALCULATE_SUM);
        bookings.forEach(bookingService::calculateAndSetSum);
    }
}