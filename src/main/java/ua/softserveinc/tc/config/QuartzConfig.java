package ua.softserveinc.tc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

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
    public SimpleTriggerFactoryBean simpleTriggerFactoryBean()
    {
        SimpleTriggerFactoryBean stFactory = new SimpleTriggerFactoryBean();
        stFactory.setJobDetail(invokeCalculateSum().getObject());
        stFactory.setRepeatInterval(1);
        stFactory.setRepeatCount(0);
        return stFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean()
    {
        SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
        scheduler.setTriggers(simpleTriggerFactoryBean().getObject());
        return scheduler;
    }
}
