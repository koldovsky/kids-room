package ua.softserveinc.tc.service;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;

import java.util.List;

/**
 * Created by Nestor on 07.05.2016.
 */
@Component
public interface RoomService extends BaseService<Room> {
    List<Event> getAllEventsInRoom(Room room);
}
