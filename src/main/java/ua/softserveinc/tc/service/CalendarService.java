package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.entity.Event;

import java.util.List;

/**
 * Created by dima- on 11.05.2016.
 */
public interface CalendarService {
    Long create(final Event event);

    List<EventDto> findEventByRoomId(final long roomId);

    void updateEvent(final Event event);

    void deleteEvent(final Event event);

    List<EventDto> createRecurrentEvents(final RecurrentEventDto recurrentEventDto);

    String getRoomWorkingHours(final long id);

    RecurrentEventDto getRecurrentEventForEditingById(long recurrentEventId);
}
