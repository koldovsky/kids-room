package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.dto.EventsCreatingResultsDto;
import ua.softserveinc.tc.dto.RecurrentEventDto;
import ua.softserveinc.tc.dto.MonthlyEventDto;
import ua.softserveinc.tc.entity.Event;

import java.util.List;

public interface CalendarService {

    Long create(final Event event);

    void add(Event event);

    List<EventDto> findEventByRoomId(final long roomId);

    void updateEvent(final Event event);

    List<Event> findByName(String name);

    void deleteEvent(final Event event);

    List<EventDto> createRecurrentEvents(
            final RecurrentEventDto recurrentEventDto);

    EventsCreatingResultsDto createMonthlyEvents(final MonthlyEventDto monthlyEventDto);

    String getRoomWorkingHours(final long id);

    EventDto getRecurrentEventForEditingById(long recurrentEventId);

    /**
     * Return capacity (number of people) of the room
     *
     * @param id id the room
     * @return capacity of the room
     */
    String getRoomCapacity(long id);
}
