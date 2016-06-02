package ua.softserveinc.tc.constants.ColumnConstants;

public interface EventConst {
    String TABLENAME = "events";

    String ID = "id_event";
    String NAME = "name";
    String START_TIME = "start_time";
    String END_TIME = "end_time";
    //String ROOM = "id_room";
    String AGE_LOW = "age_low";
    String AGE_HIGH = "age_high";
    String DESCRIPTION = "description";
    String ID_ROOM = RoomConst.ID_ROOM;

    String REPOSITORY = "eventDao";
}
