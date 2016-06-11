package ua.softserveinc.tc.constants;

/**
 * Created by Chak on 05.05.2016.
 */
public final class RoomConstants {

    public static final String TABLE_NAME_ROOMS = "rooms";

    public static final String TABLE_NAME_PRICES = "prices";

    public static final String ID_ROOM = "id_room";

    public static final String NAME_ROOM = "name_room";

    public static final String ADDRESS_ROOM = "address_room";

    public static final String CITY_ROOM = "city_room";

    public static final String PHONE_ROOM = "phone_room";

    public static final String CAPACITY_ROOM = "capacity_room";

    public static final String WORKING_START_HOUR = "working_start_hour";

    public static final String WORKING_END_HOUR = "working_end_hour";

    private RoomConstants() {
    }

    public static final class View {

        public static final String ROOMS = "rooms";

        public static final String ROOM_ID = "roomId";
    }
}
