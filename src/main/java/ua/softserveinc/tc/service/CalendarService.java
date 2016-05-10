package ua.softserveinc.tc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.softserveinc.tc.dao.EventDao;
import ua.softserveinc.tc.dao.RoomDao;
import ua.softserveinc.tc.dto.EventDTO;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dima- on 07.05.2016.
 */
@Service
public class CalendarService {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private RoomService roomService;

    public final void create(final EventDTO eventDTO) {
        Event event = new Event();
        event = eventMapper.toEntity(eventDTO);
        event.setRoom(roomDao.findById(eventDTO.getRoomId()));
        eventDao.create(event);
    }


    public final List<EventDTO> findByRoomId(final long roomId) {

        Room room = roomDao.findById(roomId);
        List<EventDTO> result = new ArrayList<EventDTO>();
        List<Event> listOfMarks = roomService.getAllEventsInRoom(room);

        return result;
    }

}
