package ua.softserveinc.tc.constants;

public interface ChildConstants {

    String TABLE_NAME = "children";

    String ID_CHILD = "id_child";

    String FIRST_NAME = "first_name_child";

    String LAST_NAME = "last_name_child";

    String DATE_OF_BIRTH = "date_of_birth_child";

    String COMMENT = "comment";

    String ID_PARENT = UserConstants.Entity.ID_USER;

    String ENABLED = "enabled";

    String DATE_FORMAT = "dd.MM.yyyy";

    String PROFILE_IMG = "profile_image";

    String GENDER = "gender";

    interface View {

        String ALL_KIDS_URL = "/allkidslist";

        String ALL_KIDS = "allkidslist";

        String MY_KIDS = "mykids";

        String KID_REGISTRATION = "registerkid";

        String KID_EDITING = "editmykid";

        String MY_KIDS_LIST_ATTRIBUTE = "kids";

        String KID_ATTRIBUTE = "kid";

        String KID_PROFILE = "profile";
    }
}