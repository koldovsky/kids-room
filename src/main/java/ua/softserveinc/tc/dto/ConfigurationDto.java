package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.util.ApplicationConfigurator;

/**
 * Created by Nestor on 11.06.2016.
 * transfer object to pass application configs to and from Administrator
 */
public class ConfigurationDto {

    private Integer kidsMinAge;
    private Integer kidsMaxAge;

    private Integer minutesToCalculateBookingsEveryDay;
    private Integer hourToCalculateBookingsEveryDay;

    private Integer minutesToSendEmailReport;
    private Integer hourToSendEmailReport;
    private Integer dayToSendEmailReport;

    private Integer daysToCleanUpBookings;

    private Integer hourToCleanUpBookings;
    private Integer minutesToCleanUpBookings;

    private Integer minPeriodSize;
    private String serverName;

    private Integer maxUploadImgSizeMb;

    private String errorMsg;

    public ConfigurationDto(){
        //empty constructor for instantiating in controller
    }

    public ConfigurationDto(ApplicationConfigurator appConfig){
        this.kidsMinAge = appConfig.getKidsMinAge();
        this.kidsMaxAge = appConfig.getKidsMaxAge();
        this.minutesToCalculateBookingsEveryDay = appConfig.getMinutesToCalculateBookingsEveryDay();
        this.hourToCalculateBookingsEveryDay = appConfig.getHourToCalculateBookingsEveryDay();
        this.minutesToSendEmailReport = appConfig.getMinutesToSendEmailReport();
        this.hourToSendEmailReport = appConfig.getHourToSendEmailReport();
        this.dayToSendEmailReport = appConfig.getDayToSendEmailReport();
        this.daysToCleanUpBookings = appConfig.getDaysToCleanUpBookings();
        this.hourToCleanUpBookings = appConfig.getHourToCleanUpBookings();
        this.minutesToCleanUpBookings = appConfig.getMinutesToCleanUpBookings();
        this.minPeriodSize = appConfig.getMinPeriodSize();
        this.serverName = appConfig.getServerName();
        this.maxUploadImgSizeMb = appConfig.getMaxUploadImgSizeMb();
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

    public Integer getDaysToCleanUpBookings() {
        return daysToCleanUpBookings;
    }

    public void setDaysToCleanUpBookings(Integer daysToCleanUpBookings) {
        this.daysToCleanUpBookings = daysToCleanUpBookings;
    }

    public Integer getHourToCleanUpBookings() {
        return hourToCleanUpBookings;
    }

    public void setHourToCleanUpBookings(Integer hourToCleanUpBookings) {
        this.hourToCleanUpBookings = hourToCleanUpBookings;
    }

    public Integer getMinutesToCleanUpBookings() {
        return minutesToCleanUpBookings;
    }

    public void setMinutesToCleanUpBookings(Integer minutesToCleanUpBookings) {
        this.minutesToCleanUpBookings = minutesToCleanUpBookings;
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


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getMaxUploadImgSizeMb() {
        return maxUploadImgSizeMb;
    }

    public void setMaxUploadImgSizeMb(Integer maxUploadImgSizeMb) {
        this.maxUploadImgSizeMb = maxUploadImgSizeMb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfigurationDto that = (ConfigurationDto) o;

        if (!kidsMinAge.equals(that.kidsMinAge)) {
            return false;
        }
        if (!kidsMaxAge.equals(that.kidsMaxAge)) {
            return false;
        }
        if (!minutesToCalculateBookingsEveryDay.equals(that.minutesToCalculateBookingsEveryDay)) {
            return false;
        }
        if (!hourToCalculateBookingsEveryDay.equals(that.hourToCalculateBookingsEveryDay)) {
            return false;
        }
        if (!minutesToSendEmailReport.equals(that.minutesToSendEmailReport)) {
            return false;
        }
        if (!hourToSendEmailReport.equals(that.hourToSendEmailReport)) {
            return false;
        }
        if (!dayToSendEmailReport.equals(that.dayToSendEmailReport)) {
            return false;
        }
        if (!minPeriodSize.equals(that.minPeriodSize)) {
            return false;
        }
        return serverName.equals(that.serverName);

    }

    @Override
    public int hashCode() {
        int result = kidsMinAge.hashCode();
        result = 31 * result + kidsMaxAge.hashCode();
        result = 31 * result + minutesToCalculateBookingsEveryDay.hashCode();
        result = 31 * result + hourToCalculateBookingsEveryDay.hashCode();
        result = 31 * result + minutesToSendEmailReport.hashCode();
        result = 31 * result + hourToSendEmailReport.hashCode();
        result = 31 * result + dayToSendEmailReport.hashCode();
        result = 31 * result + minPeriodSize.hashCode();
        result = 31 * result + serverName.hashCode();
        return result;
    }
}
