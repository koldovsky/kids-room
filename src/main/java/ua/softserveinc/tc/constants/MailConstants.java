package ua.softserveinc.tc.constants;

/**
 * Created by Chack on 06.06.2016.
 */
public interface MailConstants {

    String LINK = "link";
    String UTF_8 = "UTF-8";
    String HTTP = "http://";
    String EMAIL_TEMPLATE = "/emailTemplates/";

    String MY_BOOKINGS_LINK = "/mybookings";
    String CONFIRM_USER_LINK = "/confirm?token=";
    String CHANGE_PASS_LINK = "/changePassword?token=";
    String CONFIRM_MANAGER_LINK = "/confirm-manager?token=";

    String PAYMENT_VM = "paymentInfo.vm";
    String CONFIRM_USER_VM = "confirmEmail.vm";
    String CHANGE_PASS_VM = "changePassword.vm";
    String CONFIRM_MANAGER_VM = "confirmManager.vm";

    String PAYMENT_INFO_SUBJECT = "Payment info";
}
