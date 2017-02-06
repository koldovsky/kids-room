package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.DayOff;
import ua.softserveinc.tc.entity.Room;

import java.time.LocalDate;
import java.util.Set;

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

    public static DayOff createDayOff(long id, LocalDate startDate, LocalDate endDate, String name) {
        DayOff dayOff = new DayOff();
        dayOff.setId(id);
        dayOff.setStartDate(startDate);
        dayOff.setEndDate(endDate);
        dayOff.setName(name);
        return dayOff;
    }

}
