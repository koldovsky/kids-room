package ua.softserveinc.tc.constants;

/**
 * Created by Chack on 06.06.2016.
 */
public interface MailConstants {

    String EMAIL_TEMPLATE = "/emailTemplate/";

    String HTTP = "http://";

    String LINK = "link";

    String CONFIRM_USER_LINK = "/confirm?token=";
    String CONFIRM_MANAGER_LINK = "/confirm-manager?token=";
    String MY_BOOKINGS_LINK = "/mybookings";
    String CHANGE_PASS_LINK = "/changePassword?token=";

    String PAYMENT_VM = "paymentInfo.vm";
    String CONFIRM_USER_VM = "confirmEmail.vm";
    String CONFIRM_MANAGER_VM = "confirmManager.vm";
    String CHANGE_PASS_VM = "changePassword.vm";

    String UTF_8 = "UTF-8";
}
