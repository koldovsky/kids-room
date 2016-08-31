package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.BookingDto;
import ua.softserveinc.tc.entity.Room;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface RoomService extends BaseService<Room> {

    void saveOrUpdate(Room room);

    Map<String, String> getBlockedPeriods(Room room, Calendar start, Calendar end);

    Boolean isPossibleUpdate(BookingDto bookingDto);

    Integer getAvailableSpaceForPeriod(Date dateLo, Date dateHi, Room room);

    List<Room> getActiveRooms();
}
