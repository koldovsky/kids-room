package ua.softserveinc.tc.constants;

/**
 * Created by Demian on 07.06.2016.
 */
public final class QuartzConstants {

    public static final String TASK = "task";

    public static final String CALCULATE_SUM = "calculateSum";
    public static final String CALCULATE_SUM_TRIGGER = "calculateSumTrigger";

    public static final String CLEAN_UP_BOOKINGS = "cleanUpBookings";
    public static final String CLEAN_UP_BOOKINGS_TRIGGER = "cleanUpBookingsTrigger";

    public static final String SEND_PAYMENT_INFO = "sendPaymentInfo";
    public static final String SEND_PAYMENT_INFO_TRIGGER = "sendPaymentInfoTrigger";

    public static final String QUARTZ_PACKAGE = "ua.softserveinc.tc/quartz";

    private QuartzConstants() {
    }
}
