package ua.softserveinc.tc.service;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface RoomService extends BaseService<Room> {

    List<Event> getAllEventsInRoom(Room room);

    List<Room> findByManager(User manager);

    Map<String, String> getBlockedPeriods(Room room, Calendar start, Calendar end);

    Map<String, String> getBlockedPeriodsForWeek(Room room);

    Boolean isPeriodAvailable(Date dateLo, Date dateHi, Room room);

    Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room);
}
