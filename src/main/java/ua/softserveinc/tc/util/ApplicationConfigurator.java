package ua.softserveinc.tc.util;

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
     * @return List of allowed e-mail domains
     */
    String[] getAllowedDomainsList();

    Integer getMinPeriodSize();

    String getServerName();

    int getMinutesToCalculateBookingsEveryDay();

    int getHourToCalculateBookingsEveryDay();

    int getMinutesToSendEmailReport();

    int getHourToSendEmailReport();

    int getDayToSendEmailReport();

}
