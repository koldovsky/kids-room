package ua.softserveinc.tc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import ua.softserveinc.tc.constants.QuartzConstants;
import ua.softserveinc.tc.util.ApplicationConfigurator;

/**
 * Created by Demian on 03.06.2016.
 */
@Configuration
@ComponentScan(QuartzConstants.QUARTZ_PACKAGE)
public class QuartzConfig {
    @Autowired
    ApplicationConfigurator configurator;

    @Bean
    public MethodInvokingJobDetailFactoryBean invokeCalculateSum() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName(QuartzConstants.CALCULATE_SUM);
        obj.setTargetMethod(QuartzConstants.TASK);
        return obj;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean invokeSendPaymentInfo() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName(QuartzConstants.SEND_PAYMENT_INFO);
        obj.setTargetMethod(QuartzConstants.TASK);
        return obj;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean invokeCleanUpBookings() {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName(QuartzConstants.CLEAN_UP_BOOKINGS);
        obj.setTargetMethod(QuartzConstants.TASK);
        return obj;
    }

    @Bean
    public CronTriggerFactoryBean calculateSumTrigger() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(invokeCalculateSum().getObject());
        stFactory.setName(QuartzConstants.CALCULATE_SUM_TRIGGER);
        stFactory.setCronExpression("0 " + configurator.getMinutesToCalculateBookingsEveryDay() +
                " " + configurator.getHourToCalculateBookingsEveryDay() + " 1/1 * ? *");

        return stFactory;
    }

    @Bean
    public CronTriggerFactoryBean sendPaymentInfoTrigger() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(invokeSendPaymentInfo().getObject());
        stFactory.setName(QuartzConstants.SEND_PAYMENT_INFO_TRIGGER);
        stFactory.setCronExpression("0 " + configurator.getMinutesToSendEmailReport() +
                " " + configurator.getHourToSendEmailReport() + " " +
                configurator.getDayToSendEmailReport() + " 1/1 ? *");

        return stFactory;
    }

    @Bean
    public CronTriggerFactoryBean cleanUpBookingsTrigger() {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(invokeCleanUpBookings().getObject());
        stFactory.setName(QuartzConstants.CLEAN_UP_BOOKINGS_TRIGGER);
        stFactory.setCronExpression("0 " + configurator.getMinutesToCleanUpBookings() +
                " " + configurator.getHourToCleanUpBookings() + " 1/" +
                configurator.getDaysToCleanUpBookings() + " * ? *");

        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler() {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(calculateSumTrigger().getObject(), sendPaymentInfoTrigger().getObject(),
                cleanUpBookingsTrigger().getObject());
        return scheduler;
    }

}
