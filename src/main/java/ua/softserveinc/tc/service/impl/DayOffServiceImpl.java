package ua.softserveinc.tc.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.repo.DayOffRepository;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.DayOffService;
import ua.softserveinc.tc.service.MailService;
import ua.softserveinc.tc.service.UserService;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ua.softserveinc.tc.constants.DateConstants.WEEK_LENGTH;
import static ua.softserveinc.tc.constants.DayOffConstants.Event.DAY_OFF_DESCRIPTION;
import static ua.softserveinc.tc.constants.DayOffConstants.Event.EVENT_COLOR;
import static ua.softserveinc.tc.entity.Role.ADMINISTRATOR;
import static ua.softserveinc.tc.util.LocalDateUtil.asDate;

@Service
@Slf4j
public class DayOffServiceImpl implements DayOffService {

    @Autowired
    private MailService mailService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private UserService userService;

    @Autowired
    private DayOffRepository dayOffRepository;

    @Override
    public DayOff upsert(DayOff dayOff) {
        dayOffRepository.saveAndFlush(dayOff);
        createDayOffEvent(dayOff);
        sendSingleMail(dayOff);
        return dayOff;
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
    public void createDayOffEvent(DayOff day) {
        day.getRooms().forEach(room -> calendarService.add(Event.builder()
                .name(day.getName())
                .startTime(asDate(day.getStartDate(), room.getWorkingHoursStart()))
                .endTime(asDate(day.getEndDate(), room.getWorkingHoursEnd()))
                .room(room)
                .color(EVENT_COLOR)
                .description(DAY_OFF_DESCRIPTION)
                .build()));
    }

    @Override
    public void sendSingleMail(DayOff day) {
        LocalDate today = LocalDate.now();

        if (today.until(day.getStartDate()).getDays() < WEEK_LENGTH) {
            userService.findAll().stream()
                    .filter(user -> user.getRole() != ADMINISTRATOR)
                    .filter(User::isActive)
                    .forEach(recipient -> {
                        try {
                            mailService.sendDayOffReminderAsync(recipient, MailConstants.DAY_OFF_REMINDER, day);
                        } catch (MessagingException me) {
                            log.error("Error sending e-mail", me);
                        }
                    });
        }
    }


    @Override
    public List<DayOff> findByStartDateBetween(LocalDate startDate, LocalDate endDate) {
        return dayOffRepository.findByStartDateBetween(startDate, endDate);
    }
}
