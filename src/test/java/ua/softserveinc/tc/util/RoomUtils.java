package ua.softserveinc.tc.util;

import ua.softserveinc.tc.entity.Room;

import java.util.Arrays;
import java.util.List;

public class RoomUtils {

    public static List<Room> getListOfRooms()
    {
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();

        return Arrays.asList(room1, room2, room3);
    }

}
