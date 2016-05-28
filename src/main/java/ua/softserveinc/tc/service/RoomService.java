package ua.softserveinc.tc.service;

import org.springframework.stereotype.Component;

import ua.softserveinc.tc.entity.Event;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.User;

import java.util.List;

@Component
public interface RoomService extends BaseService<Room> {

    Room getRoomByName(String name);

    List<Event> getAllEventsInRoom(Room room);

    Room getRoombyManager(User currentManager);

    List<Room> findByCity(String city);

    List<Room> findByManger(User manager);
}
