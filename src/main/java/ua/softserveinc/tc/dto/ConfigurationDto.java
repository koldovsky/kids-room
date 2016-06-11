package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.util.ApplicationConfigurator;

/**
 * Created by Nestor on 11.06.2016.
 */
public class ConfigurationDto {
    private Integer kidsMinAge;
    private Integer kidsMaxAge;

    private Integer minutesToCalculateBookingsEveryDay;
    private Integer hourToCalculateBookingsEveryDay;

    private Integer minutesToSendEmailReport;
    private Integer hourToSendEmailReport;
    private Integer dayToSendEmailReport;

    private Integer minPeriodSize;
    private String serverName;

    public ConfigurationDto(){}

    public ConfigurationDto(ApplicationConfigurator appConfig){
        this.kidsMinAge = appConfig.getKidsMinAge();
        this.kidsMaxAge = appConfig.getKidsMaxAge();
        this.minutesToCalculateBookingsEveryDay = appConfig.getMinutesToCalculateBookingsEveryDay();
        this.hourToCalculateBookingsEveryDay = appConfig.getHourToCalculateBookingsEveryDay();
        this.minutesToSendEmailReport = appConfig.getMinutesToSendEmailReport();
        this.hourToSendEmailReport = appConfig.getHourToSendEmailReport();
        this.dayToSendEmailReport = appConfig.getDayToSendEmailReport();
        this.minPeriodSize = appConfig.getMinPeriodSize();
        this.serverName = appConfig.getServerName();
    }

    public Integer getKidsMinAge() {
        return kidsMinAge;
    }

    public void setKidsMinAge(Integer kidsMinAge) {
        this.kidsMinAge = kidsMinAge;
    }

    public Integer getKidsMaxAge() {
        return kidsMaxAge;
    }

    public void setKidsMaxAge(Integer kidsMaxAge) {
        this.kidsMaxAge = kidsMaxAge;
    }

    public Integer getMinutesToCalculateBookingsEveryDay() {
        return minutesToCalculateBookingsEveryDay;
    }

    public void setMinutesToCalculateBookingsEveryDay(Integer minutesToCalculateBookingsEveryDay) {
        this.minutesToCalculateBookingsEveryDay = minutesToCalculateBookingsEveryDay;
    }

    public Integer getHourToCalculateBookingsEveryDay() {
        return hourToCalculateBookingsEveryDay;
    }

    public void setHourToCalculateBookingsEveryDay(Integer hourToCalculateBookingsEveryDay) {
        this.hourToCalculateBookingsEveryDay = hourToCalculateBookingsEveryDay;
    }

    public Integer getMinutesToSendEmailReport() {
        return minutesToSendEmailReport;
    }

    public void setMinutesToSendEmailReport(Integer minutesToSendEmailReport) {
        this.minutesToSendEmailReport = minutesToSendEmailReport;
    }

    public Integer getHourToSendEmailReport() {
        return hourToSendEmailReport;
    }

    public void setHourToSendEmailReport(Integer hourToSendEmailReport) {
        this.hourToSendEmailReport = hourToSendEmailReport;
    }

    public Integer getDayToSendEmailReport() {
        return dayToSendEmailReport;
    }

    public void setDayToSendEmailReport(Integer dayToSendEmailReport) {
        this.dayToSendEmailReport = dayToSendEmailReport;
    }

    public Integer getMinPeriodSize() {
        return minPeriodSize;
    }

    public void setMinPeriodSize(Integer minPeriodSize) {
        this.minPeriodSize = minPeriodSize;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
