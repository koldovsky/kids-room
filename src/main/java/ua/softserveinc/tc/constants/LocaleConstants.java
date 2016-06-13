package ua.softserveinc.tc.constants;

/**
 * Created by edward on 6/7/16.
 */
public final class LocaleConstants {

    private static final String[] messages = {"kids.name", "kids.parentsName", "kids.age", "kids.phone", "kids."};

    private LocaleConstants(){}

    public static String[] getMessages(){
        return messages;
    }
}
