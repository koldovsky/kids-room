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

    String PASSWORD_INVALID = "Password is invalid";
    String PHONE_NUMBER_INVALID = "Phone number is invalid";

    String PASSWORD_REGEX = "/^[a-zA-Z0-9]{8,24}$/";
    String PHONE_NUMBER_REGEX = "/^[0-9]{10,13}$/";
}
