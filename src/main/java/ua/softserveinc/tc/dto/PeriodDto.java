package ua.softserveinc.tc.dto;

/**
 * Created by Nestor on 27.06.2016.
 */

public class PeriodDto {
    private String startDateTime;
    private String endDateTime;

    public PeriodDto(String startDateTime, String endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getStartDateTime() {

        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String toJson() {
        return "\"" + startDateTime + "\"" + ": " + "\"" + endDateTime + "\"";
    }
}
