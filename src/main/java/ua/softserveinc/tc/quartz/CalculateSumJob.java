package ua.softserveinc.tc.quartz;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by Demian on 30.05.2016.
 */
public class CalculateSumJob extends QuartzJobBean
{
    private CalculateSumTask calculateSumTask;

    public void setCalculateSumTask(CalculateSumTask calculateSumTask)
    {
        this.calculateSumTask = calculateSumTask;
    }

    protected void executeInternal(JobExecutionContext context)
    {
        calculateSumTask.execute();
    }
}
