package ua.softserveinc.tc.constants;

public interface EventConstants {

    interface Entity {
        String TABLENAME = "events";

        String ID = "id_event";
        String NAME = "name";
        String START_TIME = "start_time";
        String END_TIME = "end_time";
        String AGE_LOW = "age_low";
        String AGE_HIGH = "age_high";
        String DESCRIPTION = "description";
        String ID_ROOM = "room";

        String REPOSITORY = "eventDao";
    }

    interface View {
        String MAIN_PAGE = "index";
    }
}