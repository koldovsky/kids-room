package ua.softserveinc.tc.constants;

/**
 * Created by edward on 5/17/16.
 */
public final class SearchConstants {

    public static final String[] CHILD_SEARCH_FIELDS = {"firstName", "lastName", "parentId.firstName", "parentId.lastName"};
    public static final String[] USER_SEARCH_FIELDS = {"firstName", "lastName", "email", "phoneNumber"};
    public static final String[] BOOKING_SEARCH_FIELDS = {"idChild.firstName", "idChild.lastName"};

    public static final String CHILD_SEARCH_URL = "api/child/search";
    public static final String USER_SEARCH_URL = "api/user/search";
    public static final String BOOKING_SEARCH_URL = "api/booking/search";

    private SearchConstants() {
    }
}
