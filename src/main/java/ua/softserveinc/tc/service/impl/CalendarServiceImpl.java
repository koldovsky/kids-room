package ua.softserveinc.tc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDto;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.mapper.EventMapper;
import ua.softserveinc.tc.service.CalendarService;
import ua.softserveinc.tc.service.RoomService;

import java.util.List;

/**
 * Created by dima- on 07.05.2016.
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private RoomService roomService;

    @Autowired
    private EventMapper eventMapper;

    public Long create(final Event event) {
        eventDao.create(event);
        return event.getId();
    }

    public final List<EventDto> findByRoomId(final long roomId) {
        return eventMapper.toDto(roomService.getAllEventsInRoom(roomDao.findById(roomId)));
    }

    public final void updateEvent(Event event) {
        eventDao.update(event);
    }

    public final void deleteEvent(Event event) {
        eventDao.delete(event);
    }
}
