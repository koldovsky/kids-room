package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.util.ApplicationConfigurator;

/**
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

    private Integer hourToSendEmailReminder;
    private Integer minutesToSendEmailReminder;

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

        this.hourToSendEmailReminder = appConfig.getHoursToSendEmailReminder();
        this.minutesToSendEmailReminder = appConfig.getMinutesToSendEmailReminder();

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

    public void setMinutesToCalculateBookingsEveryDay(
            Integer minutesToCalculateBookingsEveryDay) {
        this.minutesToCalculateBookingsEveryDay = minutesToCalculateBookingsEveryDay;
    }

    public Integer getHourToCalculateBookingsEveryDay() {
        return hourToCalculateBookingsEveryDay;
    }

    public void setHourToCalculateBookingsEveryDay(
            Integer hourToCalculateBookingsEveryDay) {
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

    public Integer getHourToSendEmailReminder() {
        return hourToSendEmailReminder;
    }

    public void setHourToSendEmailReminder(Integer hourToSendEmailReminder) {
        this.hourToSendEmailReminder = hourToSendEmailReminder;
    }

    public Integer getMinutesToSendEmailReminder() {
        return minutesToSendEmailReminder;
    }

    public void setMinutesToSendEmailReminder(
            Integer minutesToSendEmailReminder) {
        this.minutesToSendEmailReminder = minutesToSendEmailReminder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationDto that = (ConfigurationDto) o;

        if (kidsMinAge != null ? !kidsMinAge.equals(that.kidsMinAge)
                : that.kidsMinAge != null) return false;
        if (kidsMaxAge != null ? !kidsMaxAge.equals(that.kidsMaxAge)
                : that.kidsMaxAge != null) return false;
        if (minutesToCalculateBookingsEveryDay != null
                ? !minutesToCalculateBookingsEveryDay
                .equals(that.minutesToCalculateBookingsEveryDay)
                : that.minutesToCalculateBookingsEveryDay != null)
            return false;
        if (hourToCalculateBookingsEveryDay != null
                ? !hourToCalculateBookingsEveryDay
                .equals(that.hourToCalculateBookingsEveryDay)
                : that.hourToCalculateBookingsEveryDay != null)
            return false;
        if (minutesToSendEmailReport != null
                ? !minutesToSendEmailReport
                .equals(that.minutesToSendEmailReport)
                : that.minutesToSendEmailReport != null)
            return false;
        if (hourToSendEmailReport != null
                ? !hourToSendEmailReport.equals(that.hourToSendEmailReport)
                : that.hourToSendEmailReport != null)
            return false;
        if (dayToSendEmailReport != null
                ? !dayToSendEmailReport.equals(that.dayToSendEmailReport)
                : that.dayToSendEmailReport != null)
            return false;
        if (daysToCleanUpBookings != null
                ? !daysToCleanUpBookings.equals(that.daysToCleanUpBookings)
                : that.daysToCleanUpBookings != null)
            return false;
        if (hourToCleanUpBookings != null
                ? !hourToCleanUpBookings.equals(that.hourToCleanUpBookings)
                : that.hourToCleanUpBookings != null)
            return false;
        if (minutesToCleanUpBookings != null
                ? !minutesToCleanUpBookings
                .equals(that.minutesToCleanUpBookings)
                : that.minutesToCleanUpBookings != null)
            return false;
        if (hourToSendEmailReminder != null
                ? !hourToSendEmailReminder.equals(that.hourToSendEmailReminder)
                : that.hourToSendEmailReminder != null)
            return false;
        if (minutesToSendEmailReminder != null
                ? !minutesToSendEmailReminder
                .equals(that.minutesToSendEmailReminder)
                : that.minutesToSendEmailReminder != null)
            return false;
        if (minPeriodSize != null
                ? !minPeriodSize.equals(that.minPeriodSize)
                : that.minPeriodSize != null)
            return false;
        if (serverName != null ? !serverName.equals(that.serverName)
                : that.serverName != null) return false;
        return maxUploadImgSizeMb != null
                ? maxUploadImgSizeMb.equals(that.maxUploadImgSizeMb)
                : that.maxUploadImgSizeMb == null;

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
