package ua.softserveinc.tc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;
import ua.softserveinc.tc.dto.ConfigurationDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


/**
 * Created by Nestor on 10.06.2016.
 * reads global configs from .properties file
 */

@Component
@Primary
@PropertySource(value = "classpath:properties/application.properties")
public class ApplicationConfiguratorPropertiesBased implements ApplicationConfigurator {

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

    @Value("${cleaning.days}")
    private Integer daysToCleanUpBookings;

    @Value("${cleaning.hour}")
    private Integer hourToCleanUpBookings;

    @Value("${cleaning.minutes}")
    private Integer minutesToCleanUpBookings;

    @Value("${booking.minPeriod}")
    private Integer minPeriodSize;

    @Value("${server.name}")
    private String serverName;

    @Value("${img.maxSize}")
    private Integer maxUploadImgSizeMb;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public ConfigurationDto getObjectDto() {
        return new ConfigurationDto(this);
    }

    @Override
    public void acceptConfiguration(ConfigurationDto cDto) throws IOException {
        if(this.getObjectDto().equals(cDto)) {
            return;
        }

        Properties properties = new Properties();

        this.kidsMinAge = cDto.getKidsMinAge();
        properties.setProperty("kids.minAge", kidsMinAge.toString());

        this.kidsMaxAge = cDto.getKidsMaxAge();
        properties.setProperty("kids.maxAge", kidsMaxAge.toString());

        this.minutesToCalculateBookingsEveryDay = cDto.getMinutesToCalculateBookingsEveryDay();
        properties.setProperty("calculation.time.minutes", minutesToCalculateBookingsEveryDay.toString());

        this.hourToCalculateBookingsEveryDay = cDto.getHourToCalculateBookingsEveryDay();
        properties.setProperty("calculation.time.hours", hourToCalculateBookingsEveryDay.toString());

        this.minutesToSendEmailReport = cDto.getMinutesToSendEmailReport();
        properties.setProperty("emailreport.time.minutes", minutesToSendEmailReport.toString());

        this.hourToSendEmailReport = cDto.getHourToSendEmailReport();
        properties.setProperty("emailreport.time.hours", hourToSendEmailReport.toString());

        this.dayToSendEmailReport = cDto.getDayToSendEmailReport();
        properties.setProperty("emailreport.time.date", dayToSendEmailReport.toString());

        this.daysToCleanUpBookings = cDto.getDaysToCleanUpBookings();
        properties.setProperty("cleaning.days", daysToCleanUpBookings.toString());

        this.hourToCleanUpBookings = cDto.getHourToCleanUpBookings();
        properties.setProperty("cleaning.hour", hourToCleanUpBookings.toString());

        this.minutesToCleanUpBookings = cDto.getMinutesToCleanUpBookings();
        properties.setProperty("cleaning.minutes", minutesToCleanUpBookings.toString());

        this.minPeriodSize = cDto.getMinPeriodSize();
        properties.setProperty("booking.minPeriod", minPeriodSize.toString());

        this.serverName = cDto.getServerName();
        properties.setProperty("server.name", serverName);

        this.maxUploadImgSizeMb = cDto.getMaxUploadImgSizeMb();
        properties.setProperty("img.maxSize", maxUploadImgSizeMb.toString());

        File propsFile = new File("src/main/resources/properties/application.properties");
        OutputStream out = new FileOutputStream(propsFile);
        DefaultPropertiesPersister persister = new DefaultPropertiesPersister();
        persister.store(properties, out, "Last changed");

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

    @Override
    public Integer getMaxUploadImgSizeMb() {
        return maxUploadImgSizeMb;
    }
}
