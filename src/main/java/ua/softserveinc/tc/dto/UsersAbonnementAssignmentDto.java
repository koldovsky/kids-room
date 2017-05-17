package ua.softserveinc.tc.dto;

public class UsersAbonnementAssignmentDto {
    private long[] userId;
    private long abonnementId;

    public UsersAbonnementAssignmentDto() {

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
