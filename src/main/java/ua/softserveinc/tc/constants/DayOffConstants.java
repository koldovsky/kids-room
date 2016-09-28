package ua.softserveinc.tc.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DayOffConstants {

    public static final class Entity {

        public static final String TABLENAME = "days_off";

        public static final String ID_DAY_OFF = "id_days_off";

        public static final String NAME = "name";

        public static final String START_DATE = "start_date";

        public static final String END_DATE = "end_date";
    }

    public static final class Mail {

        public static final String DAY_OFF = "day";

        public static final String ROOMS = "rooms";

    }

}
