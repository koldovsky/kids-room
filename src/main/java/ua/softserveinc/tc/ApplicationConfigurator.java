package ua.softserveinc.tc;

import java.util.List;

/**
 * Created by Nestor on 04.06.2016.
 */

public interface ApplicationConfigurator {
    /**
     * @return minimum allowed kid's age
     */
    Integer getKidsMinAge();

    /**
     * @return maximum allowed kid's age
     */
    Integer getKidsMaxAge();

    /**
     * @return stringified date with format "dd"
     *         marks a date to send e-mail reports to each user by Quarz
     */
    String getDateToSendEmailReport();

    /**
     * @return stringified time with format "hh:mm:ss"
     *         marks a time to (re)calculate the duration and price for all bookings
     */
    String getTimeToCalculateAllBookingsEachDay();

    /**
     * @return List of allowed e-mail domains
     */
    List<String> getAllowedDomainsList();

}
