package ua.softserveinc.tc.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import ua.softserveinc.tc.service.BookingService;

/**
 * Created by Demian on 28.05.2016.
 */
public class CalculateSumTask
{
    @Autowired
    private BookingService bookingService;

    void execute()
    {
        System.out.println("Hello, quartz!");
    }
}
