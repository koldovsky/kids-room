package ua.softserveinc.tc;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nestor on 04.06.2016.
 * Temporary class holding hardcoded global configuration values
 */

@Component
public class ApplicationConfiguratorImpl implements ApplicationConfigurator {

    List<String> allowedDomains = new ArrayList<>(Arrays.asList("softserveinc.com"));
    private Integer kidsMinAge = 3;
    private Integer kidsMaxAge = 8;
    private Integer minutesToCalculateBookingsEveryDay = 15;
    private Integer hourToCalculateBookingsEveryDay = 18;
    private Integer minutesToSendEmailReport = 30;
    private Integer hourToSendEmailReport = 19;
    private Integer dayToSendEmailReport = 20;
    private Integer minPeriodSize = 15;
    private String serverName = "localhost:8080/home";

    @Override
    public Integer getKidsMinAge() {
        return kidsMinAge;
    }

    @Override
    public Integer getKidsMaxAge() {
        return kidsMaxAge;
    }

    @Override
    public List<String> getAllowedDomainsList() {
        return allowedDomains;
    }

    @Override
    public Integer getMinPeriodSize() {
        return minPeriodSize;
    }

    @Override
    public void setMinPeriodSize(Integer minPeriodSize) {
        this.minPeriodSize = minPeriodSize;
    }

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public int getMinutesToCalculateBookingsEveryDay() {
        return minutesToCalculateBookingsEveryDay;
    }

    @Override
    public int getHourToCalculateBookingsEveryDay() {
        return hourToCalculateBookingsEveryDay;
    }

    @Override
    public int getMinutesToSendEmailReport() {
        return minutesToSendEmailReport;
    }

    @Override
    public int getHourToSendEmailReport() {
        return hourToSendEmailReport;
    }

    @Override
    public int getDayToSendEmailReport() {
        return dayToSendEmailReport;
    }
}
