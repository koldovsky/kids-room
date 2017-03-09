package ua.softserveinc.tc.dto;

/**
 * Created by daria on 02.03.2017.
 */
public class DeactivateRoomDto {
    private String id;

    private String reason;

    public DeactivateRoomDto() {
    }

    public DeactivateRoomDto(String id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
