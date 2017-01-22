package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.BookingService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.util.DateUtil;
import ua.softserveinc.tc.util.Log;

import javax.mail.MessagingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service(QuartzConstants.SEND_REMINDER)
public class SendReminderJob {

    @Log
    private static org.slf4j.Logger logger;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MailService mailService;

    private void task() {
        Date start = DateUtil.dateNow().getTime();
        Calendar calendar = DateUtil.dateNow();

        //adding 2, because "getBookings" does NOT count the last day in
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        Date end = calendar.getTime();

        Map<User, List<Booking>> bookingsGrouped = bookingService
                .getBookings(start, end, BookingState.BOOKED)
                .stream()
                .collect(Collectors.groupingBy(Booking::getUser));
        bookingsGrouped.forEach((recipient, bookings) -> {
            try {
                List<BookingDto> infoToTransfer = bookings
                        .stream()
                        .map(Booking::getDto)
                        .collect(Collectors.toList());
                mailService.sendReminder(recipient,
                        MailConstants.REMINDER_SUBJECT, infoToTransfer);

            } catch (MessagingException me){
                logger.error("Error sending e-mail", me);
            }
        });
    }
}
