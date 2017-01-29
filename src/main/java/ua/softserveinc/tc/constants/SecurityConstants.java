package ua.softserveinc.tc.constants;

public final class SecurityConstants {

    public static final String RESOURCES = "/resources/**";

    public static final String REGISTRATION = "/registration";

    public static final String ENTRY_POINT = "/";

    public static final String CONFIRM = "/confirm";

    public static final String CONFIRM_MANAGER = "/confirm-manager";

    public static final String MY_KIDS = "/mykids";

    public static final String REGISTER_KID = "/registerkid";

    public static final String EDIT_KID = "/editmykid";

    public static final String MY_BOOKINGS = "/mybookings";

    public static final String MANAGER = "/manager-**";

    public static final String ADMIN = "/adm-**";

    public static final String API = "/api/**";

    public static final String ERROR = "/error";

    public static final String SAML = "/saml/**";

    public static final String ACCESS_DENIED = "/accessDenied";

    public static final class Authentication {

        public static final String RESET_PASSWORD = "/resetPassword";

        public static final String CHANGE_PASSWORD = "/changePassword";

        public static final String LOGIN = "/login";

        public static final String LOGIN_ERROR = "/login?error";

        public static final String JSS_CHECK = "/j_spring_security_check";

        public static final String USERNAME = "j_username";

        public static final String PASSWORD = "j_password";
    }
}
