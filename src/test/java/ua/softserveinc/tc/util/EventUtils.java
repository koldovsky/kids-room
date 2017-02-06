package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import static ua.softserveinc.tc.util.DateUtil.toDate;

public class EventUtils {


    public static List<Event> getListOfEvents() throws ParseException {
        Event event1 = createEvent(1L, "New Year event", "2016-07-10 14:00:00",
                "2016-07-10 14:00:00", 3, 5, RoomUtils.getListOfRooms().get(0),
                "no description", "red");

        return Arrays.asList(event1);
    }

    public static Event createEvent(Long id, String name, String startTime, String endTime,
                                    Integer ageLow, Integer ageHigh, Room room,
                                    String description, String color) {
        Event event = new Event();
        event.setId(id);
        event.setName(name);
        event.setStartTime(toDate(startTime));
        event.setEndTime(toDate(endTime));
        event.setAgeLow(ageLow);
        event.setAgeHigh(ageHigh);
        event.setRoom(room);
        event.setDescription(description);
        event.setColor(color);

        return event;
    }

}
