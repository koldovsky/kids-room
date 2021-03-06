package ua.softserveinc.tc.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.util.DefaultPropertiesPersister;
import ua.softserveinc.tc.dto.ConfigurationDto;
import ua.softserveinc.tc.constants.UtilConstants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


/**
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

    @Value("${reminder.hour}")
    private Integer hoursToSendEmailReminder;

    @Value("${reminder.minutes}")
    private Integer minutesToSendEmailReminder;

    @Value("${booking.minPeriod}")
    private Integer minPeriodSize;

    @Value("${server.name}")
    private String serverName;

    @Value("${img.maxSize}")
    private Integer maxUploadImgSizeMb;

    @Value("${image.acceptable.format}")
    private String imageAcceptableFormats;

    @Value("${multiple.choose.login}")
    private Boolean loginWithoutSSO;

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

        kidsMinAge = cDto.getKidsMinAge();
        properties.setProperty("kids.minAge", kidsMinAge.toString());

        kidsMaxAge = cDto.getKidsMaxAge();
        properties.setProperty("kids.maxAge", kidsMaxAge.toString());

        minutesToCalculateBookingsEveryDay = cDto.getMinutesToCalculateBookingsEveryDay();
        properties.setProperty("calculation.time.minutes",
                minutesToCalculateBookingsEveryDay.toString());

        hourToCalculateBookingsEveryDay = cDto.
                getHourToCalculateBookingsEveryDay();
        properties.setProperty("calculation.time.hours", hourToCalculateBookingsEveryDay.toString());

        minutesToSendEmailReport = cDto.getMinutesToSendEmailReport();
        properties.setProperty("emailreport.time.minutes", minutesToSendEmailReport.toString());

        hourToSendEmailReport = cDto.getHourToSendEmailReport();
        properties.setProperty("emailreport.time.hours", hourToSendEmailReport.toString());

        dayToSendEmailReport = cDto.getDayToSendEmailReport();
        properties.setProperty("emailreport.time.date", dayToSendEmailReport.toString());

        daysToCleanUpBookings = cDto.getDaysToCleanUpBookings();
        properties.setProperty("cleaning.days", daysToCleanUpBookings.toString());

        hourToCleanUpBookings = cDto.getHourToCleanUpBookings();
        properties.setProperty("cleaning.hour", hourToCleanUpBookings.toString());

        minutesToCleanUpBookings = cDto.getMinutesToCleanUpBookings();
        properties.setProperty("cleaning.minutes", minutesToCleanUpBookings.toString());

        hoursToSendEmailReminder = cDto.getHourToSendEmailReminder();
        properties.setProperty("reminder.hour", hoursToSendEmailReminder.toString());

        minutesToSendEmailReminder = cDto.getMinutesToSendEmailReminder();
        properties.setProperty("reminder.minutes", minutesToSendEmailReminder.toString());

        minPeriodSize = cDto.getMinPeriodSize();
        properties.setProperty("booking.minPeriod", minPeriodSize.toString());

        serverName = cDto.getServerName();
        properties.setProperty("server.name", serverName);

        maxUploadImgSizeMb = cDto.getMaxUploadImgSizeMb();
        properties.setProperty("img.maxSize", maxUploadImgSizeMb.toString());

        properties.setProperty("image.acceptable.format", imageAcceptableFormats);

        properties.setProperty("multiple.choose.login", loginWithoutSSO.toString());

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

    @Override
    public String[] getImageAcceptableFormats() {
        return  ImageFormatNormalizator(imageAcceptableFormats);
    }

    public Integer getHoursToSendEmailReminder() {
        return hoursToSendEmailReminder;
    }

    @Override
    public Integer getMinutesToSendEmailReminder() {
        return minutesToSendEmailReminder;
    }

    public Boolean getLoginWithoutSSO() {
        return loginWithoutSSO;
    }

    /**
     * Receives a String of acceptable image formats and returns
     * a String array of normalized image formats. The String of acceptable
     * image formats must contains image extensions separated by
     * whitespace any length. Method handles following image extension:
     * .jpeg .jpg .jpe .jfif .jif .jfi .png .tiff .tif .bmp .dib. .gif
     *
     * @param formatString String of acceptable image formats
     * @return String array of normalized image formats
     */
    private static String[] ImageFormatNormalizator(String formatString) {
        String[] arraysFormats = formatString.trim().split(UtilConstants.WHITE_SPACE_REGEXP);
        for (int i = 0; i < arraysFormats.length; i++)
            switch (arraysFormats[i].toLowerCase()) {
                case "jpg":
                case "jpe":
                case "jfif":
                case "jif":
                case "jfi":
                    arraysFormats[i] = "jpeg";
                    break;
                case "tif":
                    arraysFormats[i] = "tiff";
                    break;
                case "dib":
                    arraysFormats[i] = "bmp";
                    break;
            }
        return  arraysFormats;
    }
}
