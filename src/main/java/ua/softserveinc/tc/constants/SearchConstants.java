package ua.softserveinc.tc.constants;

/**
 * Created by edward on 5/17/16.
 */
public interface SearchConstants {

    String[] CHILD_SEARCH_FIELDS = {"firstName", "lastName", "parentId.firstName", "parentId.lastName"};
    String[] USER_SEARCH_FIELDS = {"firstName", "lastName", "email", "phoneNumber"};
    String[] BOOKING_SEARCH_FIELDS = {"idChild.firstName", "idChild.lastName"};

    String CHILD_SEARCH_URL = "api/child/search";
    String USER_SEARCH_URL = "api/user/search";
    String BOOKING_SEARCH_URL = "api/booking/search";

}
