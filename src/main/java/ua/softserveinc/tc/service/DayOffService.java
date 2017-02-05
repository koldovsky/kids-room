package ua.softserveinc.tc.service;

import ua.softserveinc.tc.entity.DayOff;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

public interface DayOffService extends BaseService<DayOff>{

    /**
     * Creates {@link DayOff} in database
     * and sends information email to users, if
     * there is less than seven days till day off,
     * creates it in calendar for appropriate rooms
     *
     * @param dayOff a requested day off
     */
    void create(DayOff dayOff);

    /**
     * Updates {@link DayOff} in database
     *
     * @param dayOff a requested day off
     * @return current day
     */
    DayOff update(DayOff dayOff);

    /**
     * Check if dayOff with name and with start and end date
     * already exists or is between other dayoff.
     *
     * @param name of dayOff
     * @param startDate of dayOff event
     * */
    boolean dayOffExist(String name, LocalDate startDate);

    void delete(long id);

    DayOff findById(long id);

    List<DayOff> findByNameOrStartDate(String name, LocalDate startDate);

    /**
     * Gets all upcoming days {@link DayOff} within
     * seven days from today
     *
     * @return list of days
     */
    List<DayOff> getClosestDays();

    /**
     * Sends information email for parents and managers
     * about upcoming day off
     *
     * @param day requested day
     */
    void sendDayOffInfo(DayOff day) throws MessagingException;

    /**
     * Creates event on calendar based on day's off information
     * for appropriate rooms
     *
     * @param day requested day
     */
    void createDayOffEvent(DayOff day);

    void deleteDayOffEvent(String name);

}
