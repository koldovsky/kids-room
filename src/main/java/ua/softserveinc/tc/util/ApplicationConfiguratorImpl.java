package ua.softserveinc.tc.util;

import org.springframework.stereotype.Component;
import ua.softserveinc.tc.dto.ConfigurationDto;

/**
 * Created by Nestor on 04.06.2016.
 * Temporary class holding hardcoded global configuration values
 */

@Component
public class ApplicationConfiguratorImpl implements ApplicationConfigurator {

    private Integer kidsMinAge = 3;
    private Integer kidsMaxAge = 8;
    private Integer minutesToCalculateBookingsEveryDay = 15;
    private Integer hourToCalculateBookingsEveryDay = 18;
    private Integer minutesToSendEmailReport = 30;
    private Integer hourToSendEmailReport = 19;
    private Integer dayToSendEmailReport = 20;
    private Integer daysToCleanUpBookings = 3;
    private Integer hourToCleanUpBookings = 21;
    private Integer minutesToCleanUpBookings = 00;
    private Integer minPeriodSize = 15;
    private String serverName = "localhost:8080/home";

    @Override
    public ConfigurationDto getObjectDto() {
        return new ConfigurationDto(this);
    }

    @Override
    public void acceptConfiguration(ConfigurationDto cDto) {
        this.kidsMinAge = cDto.getKidsMinAge();
        this.kidsMaxAge = cDto.getKidsMaxAge();
        this.minutesToCalculateBookingsEveryDay = cDto.getMinutesToCalculateBookingsEveryDay();
        this.hourToCalculateBookingsEveryDay = cDto.getHourToCalculateBookingsEveryDay();
        this.minutesToSendEmailReport = cDto.getMinutesToSendEmailReport();
        this.hourToSendEmailReport = cDto.getHourToSendEmailReport();
        this.dayToSendEmailReport = cDto.getDayToSendEmailReport();
        this.daysToCleanUpBookings = cDto.getDaysToCleanUpBookings();
        this.hourToCleanUpBookings = cDto.getHourToCleanUpBookings();
        this.minutesToCleanUpBookings = cDto.getMinutesToCleanUpBookings();
        this.minPeriodSize = cDto.getMinPeriodSize();
        this.serverName = cDto.getServerName();

    }

    @Override
    public Integer getKidsMinAge() {
        return kidsMinAge;
    }

    @Override
    public Integer getKidsMaxAge() {
        return kidsMaxAge;
    }


    @Override
    public Integer getMinPeriodSize() {
        return minPeriodSize;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public Integer getMinutesToCalculateBookingsEveryDay() {
        return minutesToCalculateBookingsEveryDay;
    }

    @Override
    public Integer getHourToCalculateBookingsEveryDay() {
        return hourToCalculateBookingsEveryDay;
    }

    @Override
    public Integer getMinutesToSendEmailReport() {
        return minutesToSendEmailReport;
    }

    @Override
    public Integer getHourToSendEmailReport() {
        return hourToSendEmailReport;
    }

    @Override
    public Integer getDayToSendEmailReport() {
        return dayToSendEmailReport;
    }

    @Override
    public Integer getDaysToCleanUpBookings() {
        return daysToCleanUpBookings;
    }

    @Override
    public Integer getHourToCleanUpBookings() {
        return hourToCleanUpBookings;
    }

    @Override
    public Integer getMinutesToCleanUpBookings() {
        return minutesToCleanUpBookings;
    }
}
