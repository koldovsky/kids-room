package ua.softserveinc.tc.constants;



/**
 * Created by Nestor on 13.05.2016.
 */
public interface ValidationConst {
    String INAPPROPRIATE_AGE = "Inappropriate age";
    String EMPTY_FIELD_OR_SPACE = "Required field is empty or contains a whitespace";
    String ACCOUNT_REMOVED = "Child's account was deactivated";
    String PARENT_NOT_SET = "Failed to fetch parent's ID";
    String NAME_ERROR = "Error in name";

    String PASSWORD_REGEX = "^(\\S){8,24}$";
    String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    String EMAIL_REGEX = "^(\\w){1,60}[@][s]oft[s]erveinc[.]com$";
}
