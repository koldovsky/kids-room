package ua.softserveinc.tc.service.impl;

import org.mockito.cglib.core.Local;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.constants.MailConstants;
import ua.softserveinc.tc.dao.DayOffDao;
import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.service.*;
import ua.softserveinc.tc.util.EventBuilder;
import ua.softserveinc.tc.util.Log;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;
import static ua.softserveinc.tc.constants.DateConstants.WEEK_LENGTH;
import static ua.softserveinc.tc.constants.DayOffConstants.Event.DAY_OFF_DESCRIPTION;
import static ua.softserveinc.tc.constants.DayOffConstants.Event.EVENT_COLOR;
import static ua.softserveinc.tc.entity.Role.ADMINISTRATOR;
import static ua.softserveinc.tc.util.LocalDateUtil.asDate;

@Service
public class DayOffServiceImpl extends BaseServiceImpl<DayOff> implements DayOffService {

    @Autowired
    private MailService mailService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private DayOffDao dayOffDao;

    @Log
    private static Logger log;

    @Override
    public void create(DayOff dayOff) {
        LocalDate today = LocalDate.now();
        dayOffDao.create(dayOff);
        createDayOffEvent(dayOff);

        if (DAYS.between(today, dayOff.getStartDate()) < WEEK_LENGTH) {
            sendDayOffInfo(dayOff);
        }
    }

    @Override
    public DayOff update(DayOff dayOff) {
        LocalDate today = LocalDate.now();
        DayOff day = dayOffDao.findById(dayOff.getId());
        deleteDayOffEvent(day.getName());
        createDayOffEvent(dayOff);

        if (DAYS.between(today, dayOff.getStartDate()) < WEEK_LENGTH) {
            sendDayOffInfo(dayOff);
        }
        return dayOffDao.update(dayOff);
    }

    @Override
    public DayOff findById(long id) {
        return dayOffDao.findById(id);
    }

    @Override
    public void delete(long id) {
        String eventName = findById(id).getName();
        dayOffDao.delete(id);
        deleteDayOffEvent(eventName);
    }

    @Override
    public void deleteDayOffEvent(String name) {
        for (Event event : calendarService.findByName(name)) {
            calendarService.deleteEvent(event);
        }
    }

    @Override
    public List<DayOff> getClosestDays() {
        LocalDate today = LocalDate.now();

        return dayOffDao.findAll().stream()
                .filter(day -> day.getStartDate().isAfter(today))
                .filter(day -> DAYS.between(today,
                        day.getStartDate()) == WEEK_LENGTH)
                .sorted(Comparator.comparing(DayOff::getStartDate))
                .collect(Collectors.toList());
    }

    @Override
    public void sendDayOffInfo(DayOff day) {
        List<User> activeUsers = userService
                .findByActiveTrueAndRoleNot(ADMINISTRATOR);
        for (User recipient : activeUsers) {
            try {
                mailService.sendDayOffReminderAsync(
                        recipient, MailConstants.DAY_OFF_REMINDER, day);
            } catch (MessagingException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void createDayOffEvent(DayOff day) {
        for (Room room : day.getRooms()) {
            Event event = new EventBuilder.Builder()
                    .setName(day.getName())
                    .setRoom(room)
                    .setStartTime(asDate(day.getStartDate(), room.getWorkingHoursStart()))
                    .setEndTime(asDate(day.getEndDate(), room.getWorkingHoursEnd()))
                    .setColor(EVENT_COLOR)
                    .setDescription(DAY_OFF_DESCRIPTION)
                    .build();
            eventService.create(event);
        }
    }

}
