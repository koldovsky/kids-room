package ua.softserveinc.tc.quartz;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;
import ua.softserveinc.tc.util.Log;

import javax.mail.MessagingException;

import static ua.softserveinc.tc.entity.Role.ADMINISTRATOR;

/**
 * Class that is related to Quartz Scheduling and
 * runs its method every day at 5:30 AM
 */
@Service(QuartzConstants.SEND_DAY_OFF_REMINDER)
public class SendDayOffReminderInfo {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private DayOffService dayOffService;

    @Log
    private static Logger log;

    /**
     * Sends information email for parents and managers
     * about closest days off
     */
    private void task() {
        userService.findByActiveTrueAndRoleNot(ADMINISTRATOR).stream()
                .forEach(recipient -> dayOffService.getClosestDays().forEach(day -> {
                    try {
                        mailService.sendDayOffReminder(recipient, MailConstants.DAY_OFF_REMINDER, day);
                    } catch (MessagingException me) {
                        log.error("Error sending e-mail", me);
                    }
                }));
    }
}
