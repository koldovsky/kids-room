package ua.softserveinc.tc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;
import ua.softserveinc.tc.dto.ConfigurationDto;
import ua.softserveinc.tc.server.exception.ResourceNotFoundException;

import java.io.*;
import java.util.Calendar;
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

    @Value("${booking.minPeriod}")
    private Integer minPeriodSize;

    @Value("${server.name}")
    private String serverName;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public ConfigurationDto getObjectDto(){
        return new ConfigurationDto(this);
    }

    @Override
    public void acceptConfiguration(ConfigurationDto cDto){
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

        this.minPeriodSize = cDto.getMinPeriodSize();
        properties.setProperty("booking.minPeriod", minPeriodSize.toString());

        this.serverName = cDto.getServerName();
        properties.setProperty("server.name", serverName);
        try {
            //TODO: loading from classpath fails for whatever reason; try to fix
            File propsFile = new File("src/main/resources/properties/application.properties");
            OutputStream out = new FileOutputStream(propsFile);
            DefaultPropertiesPersister persister = new DefaultPropertiesPersister();
            persister.store(properties, out, "Last changed");
        }catch(IOException ioe){
            ioe.printStackTrace();
            //TODO: log
        }
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