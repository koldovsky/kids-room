package ua.softserveinc.tc.constants;

/**
 * Created by edward on 5/17/16.
 */
public interface SearchConstants {

    String[] childSearchFields = {"firstName", "lastName"};
    String[] userSearchFields = {"firstName", "lastName", "email", "phoneNumber"};

    String childSearchUrl = "api/user/search";
    String userSearchUrl = "api/child/search";

}
