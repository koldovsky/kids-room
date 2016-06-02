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
public class CalendarServiceImpl implements CalendarService{

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

    public final String eventsToString(final long id) {
        String buf = findByRoomId(id).toString();
        return buf.substring(1, buf.length() - 1);
    }

    public final List<EventDTO> findByRoomId(final long roomId) {

        List<EventDTO> result = new ArrayList<EventDTO>();
        List<Event> listOfEvents = eventDao.findAll();

        for(int i = 0; i < listOfEvents.size(); i++) {
            if(listOfEvents.get(i).getRoom().getId() == roomId) {
                result.add(eventMapper.toDto(listOfEvents.get(i)));
            }
        }
        return result;
    }

    public final void updateEvent(Event event) {
        eventDao.update(event);
    }

    public final void deleteEvent(Event event) {
        eventDao.delete(event);
    }
}
