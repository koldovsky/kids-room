package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.ConfigurationDto;

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

    Integer getMinPeriodSize();

    String getServerName();

    int getMinutesToCalculateBookingsEveryDay();

    int getHourToCalculateBookingsEveryDay();

    int getMinutesToSendEmailReport();

    int getHourToSendEmailReport();

    int getDayToSendEmailReport();

    ConfigurationDto getObjectDto();

    void acceptConfiguration(ConfigurationDto cDto);

}
