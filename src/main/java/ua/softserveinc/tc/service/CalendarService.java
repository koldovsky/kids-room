package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.EventDtosss;
import ua.softserveinc.tc.entity.Event;

import java.util.List;

/**
 * Created by dima- on 11.05.2016.
 */
public interface CalendarService {
    Long create(final Event event);
    List<EventDtosss> findByRoomId(final long roomId);
    String eventsToString(long id);
    void updateEvent(Event event);
    void deleteEvent(Event event);
}
