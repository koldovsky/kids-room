package ua.softserveinc.tc.quartz;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.repo.UserRepository;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.service.MailService;

import javax.mail.MessagingException;

import static ua.softserveinc.tc.entity.Role.ADMINISTRATOR;

/**
 * Class that is related to Quartz Scheduling and
 * runs its method every day at 5:30 AM
 */
@Service(QuartzConstants.SEND_DAY_OFF_REMINDER)
@Slf4j
public class SendDayOffReminderInfo {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DayOffService dayOffService;

    /**
     * Sends information email for parents and managers
     * about closest days off
     */
    private void task() {
        userRepository.findByActiveTrueAndRoleNot(ADMINISTRATOR).stream()
                .forEach(recipient -> dayOffService.getClosestDays().forEach(day -> {
                    try {
                        mailService.sendDayOffReminder(recipient, MailConstants.DAY_OFF_REMINDER, day);
                    } catch (MessagingException me) {
                        log.error("Error sending e-mail", me);
                    }
                }));
    }
}
