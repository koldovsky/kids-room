package ua.softserveinc.tc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;


/**
 * Created by Nestor on 10.06.2016.
 * reads global configs from .properties file
 */

@Component
@Primary
@PropertySource(value = "classpath:properties/application.properties")
public class ApplicationConfiguratorPropertiesBased implements ApplicationConfigurator {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("#{'${allowed.domains}'.split(',')}")
    private String[] allowedDomains;

    @Value("${kids.minAge}")
    private Integer kidsMinAge;

    @Value("${kids.maxAge}")
    private Integer kidsMaxAge;

    @Value("${calculation.time.minutes}")
    private Integer minutesToCalculateBookingsEveryDay;

    @Value("${calculation.time.hours}")
    private Integer hourToCalculateBookingsEveryDay;

    @Value("${emailreport.time.minutes}")
    private Integer minutesToSendEmailReport;

    @Value("${emailreport.time.hours}")
    private Integer hourToSendEmailReport;

    @Value("${emailreport.time.date}")
    private Integer dayToSendEmailReport;

    @Value("${booking.minPeriod}")
    private Integer minPeriodSize;

    @Value("${server.name}")
    private String serverName;

    @Override
    public Integer getKidsMinAge() {
        return kidsMinAge;
    }

    @Override
    public Integer getKidsMaxAge() {
        return kidsMaxAge;
    }

    @Override
    public String[] getAllowedDomainsList() {
        return allowedDomains;
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
