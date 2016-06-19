package ua.softserveinc.tc.constants;

/**
 * Created by Chak on 05.05.2016.
 */
public final class UserConstants {
    private UserConstants() {
    }

    public static final class Entity {

        public static final String TABLE_NAME_USER = "users";
        public static final String ID_USER = "id_user";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String PHONE = "phone_number";
        public static final String ROLE = "role";
        public static final String NQ_FIND_USER_BY_EMAIL = "findUserByEmail";
        public static final String CONFIRMED = "confirmed";
        public static final String ACTIVE = "active";
        public static final String USER = "user";
        public static final String ROOMS = "rooms";
        public static final String KIDS = "kids";
        public static final String USERID = "userId";

        private Entity (){}
    }

    public static final class Model {

        public static final String LOGIN_VIEW = "login";
        public static final String REGISTRATION_VIEW = "registration";
        public static final String SUCCESS_VIEW = "success";
        public static final String RULES_VIEW = "rules";
        public static final String UPDATE_PASS_VIEW = "updatePassword";
        public static final String MY_BOOKINGS_VIEW = "mybookings";
        public static final String FORGOT_PASSWORD_VIEW = "forgotPassword";
        public static final String RESEND_MAIL_VIEW = "resend-mail";
        public static final String USER_DETAILS_SERVICE = "userDetailsService";
        public static final String ATRIBUTE_CONFIG = "config";

        private Model() {}
    }

    public static final class Role {

        public static final String ROLE_USER = "USER";
        public static final String ROLE_MANAGER = "MANAGER";
        public static final String ROLE_ADMINISTRATOR = "ADMINISTRATOR";

        private Role(){}
    }
}
