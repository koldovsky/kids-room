package ua.softserveinc.tc.constants;

/**
 * Created by edward on 5/17/16.
 */
public final class SearchConstants {

    private static final String[] CHILD_SEARCH_FIELDS = {"firstName", "lastName", "parentId.firstName", "parentId.lastName"};
    private static final String[] USER_SEARCH_FIELDS = {"firstName", "lastName", "email", "phoneNumber"};
    private static final String[] BOOKING_SEARCH_FIELDS = {"child.firstName", "child.lastName"};

    public static final String CHILD_SEARCH_URL = "api/child/search";
    public static final String USER_SEARCH_URL = "api/user/search";
    public static final String BOOKING_SEARCH_URL = "api/booking/search";

    private SearchConstants() {
    }

    public static String[] getChildSearchFields() {
        return CHILD_SEARCH_FIELDS;
    }

    public static String[] getUserSearchFields() {
        return USER_SEARCH_FIELDS;
    }

    public static String[] getBookingSearchFields() {
        return BOOKING_SEARCH_FIELDS;
    }
}
