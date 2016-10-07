package ua.softserveinc.tc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Role;
import ua.softserveinc.tc.repo.DayOffRepository;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.constants.DateConstants.WEEK_LENGTH;

@Service
@Slf4j
public class DayOffServiceImpl implements DayOffService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @Autowired
    private DayOffRepository dayOffRepository;

    @Override
    public DayOff upsert(DayOff dayOff) {
        return dayOffRepository.saveAndFlush(dayOff);
    }

    @Override
    public DayOff findById(long id) {
        return dayOffRepository.findOne(id);
    }

    @Override
    public List<DayOff> findByNameOrStartDate(String name, LocalDate startDate) {
        return dayOffRepository.findByNameOrStartDate(name, startDate);
    }

    @Override
    public boolean dayOffExist(String name, LocalDate startDate) {
        if (findByNameOrStartDate(name, startDate).isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void delete(long id) {
        dayOffRepository.delete(id);
    }

    @Override
    public List<DayOff> findAll() {
        return dayOffRepository.findAll();
    }

    @Override
    public List<DayOff> getClosestDays() {
        LocalDate today = LocalDate.now();

        return dayOffRepository.findAll().stream()
                .filter(day -> day.getStartDate().isAfter(today))
                .filter(day -> today.until(day.getStartDate()).getDays() <= WEEK_LENGTH)
                .sorted(Comparator.comparing(DayOff::getStartDate))
                .collect(Collectors.toList());
    }

    @Override
    public void sendSingleMail(DayOff day) {
        userService.findAll().stream()
                .filter(user -> !(user.getRole().equals(Role.ADMINISTRATOR)))
                .forEach(recipient -> {
                    try {
                        mailService.sendDayOffReminderAsync(recipient, MailConstants.DAY_OFF_REMINDER, day);
                    } catch (MessagingException me) {
                        log.error("Error sending e-mail", me);
                    }
                });
    }


    @Override
    public List<DayOff> findByStartDateBetween(LocalDate startDate, LocalDate endDate) {
        return dayOffRepository.findByStartDateBetween(startDate, endDate);
    }
}
