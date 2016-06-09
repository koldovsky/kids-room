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

    Room getRoomByName(String name);

    List<Event> getAllEventsInRoom(Room room);

    Room getRoomByManager(User currentManager);

    List<Room> findByCity(String city);

    List<Room> findByManger(User manager);

    Map<String, String> getBlockedPeriodsForWeek(Room room);

    Map<String, String> getBlockedPeriodsForDay(Room room, Calendar day);

    Boolean isPeriodAvailable(Date dateLo, Date dateHi, Room room);
}
