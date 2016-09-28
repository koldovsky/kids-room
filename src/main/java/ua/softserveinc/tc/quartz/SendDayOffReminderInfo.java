package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.service.MailService;

import java.time.LocalDate;

@Service(QuartzConstants.SEND_DAY_OFF_REMINDER)
public class SendDayOffReminderInfo {

    @Autowired
    private MailService mailService;

    private void task() {
        LocalDate today = LocalDate.now();

    }
}
