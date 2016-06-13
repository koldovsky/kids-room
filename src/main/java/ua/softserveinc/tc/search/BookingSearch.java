package ua.softserveinc.tc.search;

import org.springframework.stereotype.Repository;
import ua.softserveinc.tc.constants.SearchConstants;
import ua.softserveinc.tc.entity.Booking;

/**
 * Created by edward on 6/9/16.
 */
@Repository
public class BookingSearch extends BaseSearch<Booking> {

    public BookingSearch() {
        searchFields = SearchConstants.getBookingSearchFields();
    }

    @Override
    public Class<Booking> getTClass() {
        return Booking.class;
    }

}
