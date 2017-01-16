package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventUtils {


    public static List<Event> getListOfEvents() throws ParseException {
        Event event1 = new Event();

        event1.setId(1L);
        event1.setName("New Year event");
        event1.setAgeHigh(5);
        event1.setAgeLow(3);
        event1.setColor("red");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
        String startDateInString = "2016-07-10 14:00:00";
        Date startDate = sdf.parse(startDateInString);

        String endDateInString = "2016-07-10 14:00:00";
        Date endDate = sdf.parse(endDateInString);

        event1.setStartTime(startDate);
        event1.setEndTime(endDate);
        event1.setRoom(RoomUtils.getListOfRooms().get(0));
        event1.setDescription("no description");

        Event event2 = new Event();
        Event event3 = new Event();

        return Arrays.asList(event1, event2, event3);
    }

}
