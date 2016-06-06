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
    public CronTriggerFactoryBean cronTriggerFactoryBean(){
        CronTriggerFactoryBean stFactory = new CronTriggerFactoryBean();
        stFactory.setJobDetail(invokeCalculateSum().getObject());
        stFactory.setName("mytrigger");
        stFactory.setGroup("mygroup");
        stFactory.setCronExpression("0 15 18 1/1 * ? *");
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean()
    {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(cronTriggerFactoryBean().getObject());
        return scheduler;
    }
}
