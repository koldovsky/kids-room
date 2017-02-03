package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Room;

import java.time.LocalDate;
import java.util.Set;

// todo: use builder design pattern for more complex creation of Utils (such as user) ;

public class DayOffUtils {

    public static DayOff createDayOff(long id, LocalDate startDate, LocalDate endDate, String name, Set<Room> rooms) {
        DayOff dayOff = new DayOff();
        dayOff.setId(id);
        dayOff.setStartDate(startDate);
        dayOff.setEndDate(endDate);
        dayOff.setName(name);
        dayOff.setRooms(rooms);
        return dayOff;
    }

}
