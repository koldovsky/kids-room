package ua.softserveinc.tc.constants;

public final class AbonnementConstants {

    private AbonnementConstants() {
    }

    public static final class Entity {
        public static final String TABLE_NAME_ABONEMENT = "abonements";
        public static final String ID_ABONEMENT = "id_abonement";
        public static final String NAME_ABONEMENT = "name_abonement";
        public static final String HOUR = "hour";
        public static final String PRICE = "price";
        public static final String ACTIVE = "active";
        public static final String START_DATE = "start_date";
        public static final String END_DATE = "end_date";
        public static final String USER = "user";
        public static final String ABONEMENT = "abonement";
        public static final String USERS_ABONEMENTS = "users_abonements";
    }

    public static final class RestQueries {
        public static final String UPDATE_ABONNEMENT = "adm-update-abonnement";
        public static final String CREATE_ABONNEMENT = "adm-create-abonnement";
    }

}
