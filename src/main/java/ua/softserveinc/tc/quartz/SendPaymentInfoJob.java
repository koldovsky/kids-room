package ua.softserveinc.tc.quartz;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.util.Log;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static ua.softserveinc.tc.util.DateUtil.*;

/**
 * Created by Demian on 06.06.2016.
 */
@Service(QuartzConstants.SEND_PAYMENT_INFO)
public class SendPaymentInfoJob {
    @Log
    private static Logger log;

    @Autowired
    private MailService mailService;

    @Autowired
    private BookingService bookingService;

    private void task() {
        Date startDate = toDate(dateMonthAgo());
        Date endDate = toDate(dateNow());

        List<Booking> bookings = bookingService.getBookings(startDate, endDate, BookingState.COMPLETED);
        Map<User, Long> report = bookingService.generateAReport(bookings);

        report.forEach((user, sum) -> {
            try {
                mailService.sendPaymentInfo(user, MailConstants.PAYMENT_INFO_SUBJECT, sum);
            } catch (MessagingException e) {
                log.error("Error while sending email!", e);
            }
        });
    }
}