package ua.softserveinc.tc.constants;



/**
 * Created by Nestor on 13.05.2016.
 */
public interface ValidationConst {
    String PASSWORD_REGEX = "^(\\S){8,24}$";
    String PHONE_NUMBER_REGEX = "^(\\+38|8|)\\W*\\d{10}\\W*$";
    String NAME_REGEX = "^[A-Z].*";
    String EMAIL_REGEX = "^(\\w){1,60}[@][s]oft[s]erveinc[.]com$";
}
