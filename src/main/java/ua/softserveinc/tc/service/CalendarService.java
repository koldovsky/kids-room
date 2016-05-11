package ua.softserveinc.tc.service;

import ua.softserveinc.tc.dto.EventDTO;

import java.util.List;

/**
 * Created by dima- on 11.05.2016.
 */
public interface CalendarService {
    void create(final EventDTO eventDTO);
    List<EventDTO> findByRoomId(final long roomId);
}
