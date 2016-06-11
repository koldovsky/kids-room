package ua.softserveinc.tc.constants;

/**
 * Created by Chak on 05.05.2016.
 */
public interface UserConstants {
    interface Entity {
        String TABLE_NAME_USER = "users";
        String ID_USER = "id_user";
        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String EMAIL = "email";
        String PASSWORD = "password";
        String PHONE = "phone_number";
        String ROLE = "role";
        String NQ_FIND_USER_BY_EMAIL = "findUserByEmail";
        String CONFIRMED = "confirmed";
        String ACTIVE = "active";
        String USER = "user";
    }

    interface Model {
        String LOGIN_VIEW = "login";
        String REGISTRATION_VIEW = "registration";
        String SUCCESS_VIEW = "success";
        String RULES_VIEW = "rules";
        String UPDATE_PASS_VIEW = "updatePassword";
        String MY_BOOKINGS_VIEW = "mybookings";
        String FORGOT_PASSWORD_VIEW = "forgotPassword";
        String RESEND_MAIL_VIEW = "resend-mail";
        String USER_DETAILS_SERVICE = "userDetailsService";
        String ATRIBUTE_CONFIG = "config";
    }

    interface Role {
        String ROLE_USER= "USER";
        String ROLE_MANAGER= "MANAGER";
        String ROLE_ADMINISTRATOR= "ADMINISTRATOR";
    }
}
