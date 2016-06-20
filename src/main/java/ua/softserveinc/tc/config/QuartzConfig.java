package ua.softserveinc.tc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by Demian on 03.06.2016.
 */
@Configuration
@ComponentScan("ua.softserveinc.tc/quartz")
public class QuartzConfig
{
    @Bean
    public MethodInvokingJobDetailFactoryBean invokeCalculateSum()
    {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("calculateSum");
        obj.setTargetMethod("task");
        return obj;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean invokeSendPaymentInfo()
    {
        MethodInvokingJobDetailFactoryBean obj = new MethodInvokingJobDetailFactoryBean();
        obj.setTargetBeanName("sendPaymentInfo");
        obj.setTargetMethod("task");
        return obj;
    }

    @Bean
    public CronTriggerFactoryBean calculateSumTrigger()
    {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(invokeCalculateSum().getObject());
        stFactory.setName("calculateSumTrigger");
        stFactory.setCronExpression("0 15 18 1/1 * ? *");
        return stFactory;
    }

    @Bean
    public CronTriggerFactoryBean sendPaymentInfoTrigger()
    {
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(invokeSendPaymentInfo().getObject());
        stFactory.setName("sendPaymentInfoTrigger");
        stFactory.setCronExpression("0 30 19 20 1/1 ? *");
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean scheduler()
    {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(calculateSumTrigger().getObject(), sendPaymentInfoTrigger().getObject());
        return scheduler;
    }
}
