package ua.softserveinc.tc.constants;

public final class ChildConstants {

    public static final String TABLE_NAME = "children";

    public static final String ID_CHILD = "id_child";

    public static final String FIRST_NAME = "first_name_child";

    public static final String LAST_NAME = "last_name_child";

    public static final String DATE_OF_BIRTH = "date_of_birth_child";

    public static final String COMMENT = "comment";

    public static final String ID_PARENT = UserConstants.Entity.ID_USER;

    public static final String ENABLED = "enabled";

    public static final String DATE_FORMAT = "dd.MM.yyyy";

    public static final String PROFILE_IMG = "profile_image";

    public static final String GENDER = "gender";

    private ChildConstants() {
    }

    public static final class View {

        public static final String ALL_KIDS_URL = "/manager-allkidslist";

        public static final String ALL_KIDS = "allkidslist";

        public static final String MY_KIDS = "mykids";

        public static final String KID_REGISTRATION = "registerkid";

        public static final String KID_EDITING = "editmykid";

        public static final String MY_KIDS_LIST_ATTRIBUTE = "kids";

        public static final String KID_ATTRIBUTE = "kid";

        public static final String KID_PROFILE = "profile";

        private View(){}
    }
}