package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;

import javax.mail.MessagingException;

@Service(QuartzConstants.SEND_DAY_OFF_REMINDER)
public class SendDayOffReminderInfo {

    @Log
    private static org.slf4j.Logger logger;

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private DayOffService dayOffService;

    private void task() {
        userService.findAll().forEach(recipient -> dayOffService.getClosestDays().forEach(day -> {
            try {
                mailService.sendDayOffReminder(recipient, MailConstants.DAY_OFF_REMINDER, day);
            } catch (MessagingException me) {
                logger.error("Error sending e-mail", me);
            }
        }));
    }
}
