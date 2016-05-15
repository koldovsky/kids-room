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

    public final void create(final EventDTO eventDTO) {
        Event event = new Event();
        event = eventMapper.toEntity(eventDTO);
       // event.setRoom(roomDao.findById(eventDTO.getRoomId()));


        List<Room> rooms = roomDao.findAll();
        Room room = null;

        for(int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getId() == 1) {
                room = rooms.get(i);
            }
        }
        event.setRoom(room);
        eventDao.create(event);
    }

    public final String eventsToString(long id) {
        return findByRoomId(id).toString().substring(1, this.findByRoomId(id).toString().length() - 1);
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

}
