package ua.softserveinc.tc.constants;

/**
 * Created by edward on 6/7/16.
 */
public final class LocaleConstants {

    public static final String SESSION_LOCALE_ATTRIBUTE =
            "org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE";

    private static final String[] messages = {
            "kids.name",
            "kids.parentsName",
            "kids.age",
            "kids.phone",
            "kids.comment",
            "search",
            "kid.gender"
    };

  private LocaleConstants() {
  }

  public static String[] getMessages() {
    return messages;
  }
}
