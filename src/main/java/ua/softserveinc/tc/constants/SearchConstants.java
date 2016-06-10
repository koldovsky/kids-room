package ua.softserveinc.tc.constants;

/**
 * Created by edward on 5/17/16.
 */
public interface SearchConstants {

    String[] childSearchFields = {"firstName", "lastName", "parentId.firstName", "parentId.lastName"};
    String[] userSearchFields = {"firstName", "lastName", "email", "phoneNumber"};
    String[] bookingSearchFields = {"idChild.firstName", "idChild.lastName"};

    String childSearchUrl = "api/child/search";
    String userSearchUrl = "api/user/search";
    String bookingSearchUrl = "api/booking/search";

}
