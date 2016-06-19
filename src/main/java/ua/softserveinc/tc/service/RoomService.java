package ua.softserveinc.tc.service;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface RoomService extends BaseService<Room> {

    @Override
    default Room update(Room entity) {
        return saveOrUpdate(entity);
    }

    @Override
    default void create(Room entity) {
        saveOrUpdate(entity);
    }

    Room saveOrUpdate(Room room);

    Map<String, String> getBlockedPeriods(Room room, Calendar start, Calendar end);

    Map<String, String> getBlockedPeriodsForWeek(Room room);

    Boolean isPeriodAvailable(Date dateLo, Date dateHi, Room room);

    Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room);
}
