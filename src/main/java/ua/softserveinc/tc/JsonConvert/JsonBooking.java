package ua.softserveinc.tc.JsonConvert;

/**
 * Created by Петришак on 14.05.2016.
 */
public class JsonBooking {

    String start;
    Long id;


    public JsonBooking(String start) {
        this.start = start;

    }

    public JsonBooking(String start, Long id) {
        this.start = start;
        this.id = id;
    }

}
