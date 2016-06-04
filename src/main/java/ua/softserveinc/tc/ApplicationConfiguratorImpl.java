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
public class ApplicationConfiguratorImpl implements ApplicationConfigurator{

    private Integer kidsMinAge = 3;
    private Integer kidsMaxAge = 8;

    private String dateToSendEmailReport = "01";
    private String timeToCalculateAllBookingsEachDay = "23:59:00";

    List<String> allowedDomains = new ArrayList<>(Arrays.asList("softserveinc.com"));

    @Override
    public Integer getKidsMinAge() {
        return kidsMinAge;
    }

    @Override
    public Integer getKidsMaxAge() {
        return kidsMaxAge;
    }

    @Override
    public String getDateToSendEmailReport() {
        return dateToSendEmailReport;
    }

    @Override
    public String getTimeToCalculateAllBookingsEachDay() {
        return timeToCalculateAllBookingsEachDay;
    }

    @Override
    public List<String> getAllowedDomainsList() {
        return allowedDomains;
    }
}
