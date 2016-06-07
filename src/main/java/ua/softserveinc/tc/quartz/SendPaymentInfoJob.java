package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.util.DateUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by Demian on 06.06.2016.
 */
@Service("sendPaymentInfo")
public class SendPaymentInfoJob {
    @Autowired
    private DateUtil dateUtil;

    @Autowired
    private MailService mailService;

    @Autowired
    private BookingService bookingService;

    private void task() {
        String now = dateUtil.getStringDate(dateUtil.currentDate());
        String then = dateUtil.getStringDate(dateUtil.dateMonthAgo());

        List<Booking> bookings = bookingService.getBookingsByRangeOfTime(then, now);
        Map<User, Long> report = bookingService.generateAReport(bookings);

        report.forEach((user, sum) -> {
            if (user.getEmail().equals("bahrianyi@ukr.net"))
                mailService.sendPaymentInfo(user, "Payment info", sum);
        });
    }
}