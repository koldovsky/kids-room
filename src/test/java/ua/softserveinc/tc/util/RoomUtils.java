package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Room;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomUtils {

    public static Room createRoom(long id, String name, String address, String phoneNumber,
                                  Integer capacity, String workingHoursStart, String workingHoursEnd,
                                  boolean isActive) {
        Room room = new Room();
        room.setId(id);
        room.setName(name);
        room.setAddress(address);
        room.setPhoneNumber(phoneNumber);
        room.setCapacity(capacity);
        room.setWorkingHoursStart(workingHoursStart);
        room.setWorkingHoursEnd(workingHoursEnd);
        room.setActive(isActive);

        return room;
    }

    public static List<Room> getListOfRooms() {
        Room room1 = createRoom(1L, "Kvitkova", "Pasternaka 8", "+380947715038", 40,
                "07:00", "20:00", true);

        Room room2 = createRoom(2L, "Antonycha 22", "Soniachna", "+380938587190", 15,
                "07:00", "20:00", true);

        Room room3 = createRoom(3L, "Sadova 2a", "Nebesna", "+380951789603", 30,
                "07:00", "20:00", true);

        return Arrays.asList(room1, room2, room3);
    }

    public static Set<Room> getSetOfRooms() {
        return new HashSet<>(getListOfRooms());
    }
}
