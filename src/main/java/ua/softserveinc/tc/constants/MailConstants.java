package ua.softserveinc.tc.constants;

public final class MailConstants {

  public static final String LINK = "link";
  public static final String UTF_8 = "UTF-8";
  public static final String HTTP = "http://";
  public static final String EMAIL_TEMPLATE = "/emailTemplates/";
  public static final String EMAIL_BOT_ADDRESS = "kidsroom.softserve@gmail.com";

  public static final String MY_BOOKINGS_LINK = "/mybookings";
  public static final String CONFIRM_USER_LINK = "/confirm?token=";
  public static final String CHANGE_PASS_LINK = "/changePassword?token=";
  public static final String CONFIRM_MANAGER_LINK = "/confirm-manager?token=";

  public static final String PAYMENT_VM = "paymentInfo.vm";
  public static final String CONFIRM_USER_VM = "confirmEmail.vm";
  public static final String CHANGE_PASS_VM = "changePassword.vm";
  public static final String CONFIRM_MANAGER_VM = "confirmManager.vm";
  public static final String REMINDER_VM = "reminder.vm";
  public static final String DAY_OFF_REMINDER_VM = "dayOffReminder.vm";
  public static final String DEACRIVATE_ROOM_INFO = "deactivateRoom.vm";

  public static final String DAY_OFF_REMINDER = "Upcoming day off";
  public static final String PAYMENT_INFO_SUBJECT = "Payment info";
  public static final String REMINDER_SUBJECT = "You have a reservation for today!";
  public static final String CHANGE_PASS = "Change password";
  public static final String CONFIRM_REGISTRATION = "Confirmation registration";
  public static final String DEACRIVATE_ROOM_NAME = " room is unavailable";

  private MailConstants() {
  }
}
