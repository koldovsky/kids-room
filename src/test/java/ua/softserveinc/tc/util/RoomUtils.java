package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Room;

import java.util.Arrays;
import java.util.List;

public class RoomUtils {

    public static List<Room> getListOfRooms() {
        Room room1 = new Room();

        room1.setId(1L);
        room1.setName("Kvitkova");
        room1.setAddress("Pasternaka 8");
        room1.setCapacity(40);
        room1.setPhoneNumber("+380947715038");
        room1.setWorkingHoursStart("07:00'");
        room1.setWorkingHoursEnd("20:00");
        room1.setActive(true);

        Room room2 = new Room();
        Room room3 = new Room();

        return Arrays.asList(room1, room2, room3);
    }

}
