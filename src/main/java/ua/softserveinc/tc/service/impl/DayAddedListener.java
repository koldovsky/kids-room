package ua.softserveinc.tc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;

import javax.mail.MessagingException;
import javax.persistence.PostPersist;
import java.time.LocalDate;

import static ua.softserveinc.tc.constants.DateConstants.WEEK_LENGTH;

@Slf4j
public class DayAddedListener {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostPersist
    public void checkDayMailSending(DayOff day) {
        LocalDate today = LocalDate.now();

        if(today.until(day.getStartDate()).getDays()< WEEK_LENGTH) {
            new Thread(() -> userService.findAll().stream()
                    .filter(user -> !(user.getRole().equals(Role.ADMINISTRATOR)))
                    .forEach(recipient -> {
                        try {
                            mailService.sendDayOffReminder(recipient, MailConstants.DAY_OFF_REMINDER, day);
                        } catch (MessagingException me) {
                            log.error("Error sending e-mail", me);
                        }
                    })).start();
        }

    }

}
