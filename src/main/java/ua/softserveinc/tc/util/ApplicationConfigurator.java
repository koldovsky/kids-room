package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.ConfigurationDto;

import java.io.IOException;

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
     * @return a minimum period that a booking is to be made for
     */
    Integer getMinPeriodSize();

    /**
     * @return basic host url
     */
    String getServerName();


    /**
     * Each day the COMPLETED bookings are (re)calculated
     * the next 2 methods return the time of this recalculation
     *
     * @return minutes
     */
    Integer getMinutesToCalculateBookingsEveryDay();

    /**
     * @return hours
     */
    Integer getHourToCalculateBookingsEveryDay();

    /**
     * Each month an email report is scheduled to users' e-mails
     * the next 3 methods return the time of this action
     *
     * @return minutes
     */
    Integer getMinutesToSendEmailReport();

    /**
     * @return hours
     */
    Integer getHourToSendEmailReport();

    /**
     * @return date
     */
    Integer getDayToSendEmailReport();

    /**
     * Each month the old bookings are getting cleaned up
     * the next 3 methods return the time of this action
     *
     * @return date
     */
    Integer getDaysToCleanUpBookings();

    /**
     * @return hours
     */
    Integer getHourToCleanUpBookings();

    /**
     * @return minutes
     */
    Integer getMinutesToCleanUpBookings();

    /**
     * @return a maximum upload size for images
     */
    Integer getMaxUploadImgSizeMb();

    /**
     * Each day a letter is scheduled to users' e-mails reminding
     * about today's booking made by the user
     * the next 2 methods return the time of this action
     *
     * @return hours
     */
    Integer getHourToSendEmailReminder();

    /**
     * @return minutes
     */
    Integer getMinutesToSendEmailReminder();

    /**
     * Returns an object suitable for transferring (DTO)
     * contains all configuration values
     * @return dto
     */
    ConfigurationDto getObjectDto();

    /**
     * updates the configuration to the values contained in the parameter
     * @param cDto dto
     * @throws IOException
     */
    void acceptConfiguration(ConfigurationDto cDto) throws IOException;

}
