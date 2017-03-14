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
        public static final String ABONNEMENT = "abonnement";
        public static final String USERS_ABONEMENTS = "users_abonements";
    }

    public static final class RestQueries {
        public static final String UPDATE_ABONNEMENT = "adm-update-abonnement";
        public static final String SELECT_ABONNEMENTS = "adm-all-abonnements";
        public static final String CREATE_ABONNEMENT = "adm-create-abonnement";
        public static final String UPDATE_ACTIVE_STATE = "adm-active-abonnement";
    }

    public static final class Hibernate {
        public static final String ABONNEMENT_ID = "id";
        public static final String ABONNEMENT_NAME = "name";
        public static final String ABONNEMENT_HOUR = "hour";
        public static final String ABONNEMENT_PRICE = "price";
        public static final String ABONNEMENT_IS_ACTIVE = "isActive";
        public static final String ABONNEMENT_USERS = "abonementUsers";
    }

}
