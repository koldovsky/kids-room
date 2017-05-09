package ua.softserveinc.tc.dto;

public class UserAbonnementAssignmentDto {
    private long[] userId;
    private long abonnementId;

    public UserAbonnementAssignmentDto() {

    }

    public long[] getUserId() {
        return userId;
    }

    public void setUserId(long[] userId) {
        this.userId = userId;
    }

    public long getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(long abonnementId) {
        this.abonnementId = abonnementId;
    }
}
