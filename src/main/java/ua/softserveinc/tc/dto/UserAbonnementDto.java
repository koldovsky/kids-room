package ua.softserveinc.tc.dto;

public class UserAbonnementDto {
    Long[] userId;
    Long abonnementId;

    public UserAbonnementDto() {

    }

    public Long[] getUserId() {
        return userId;
    }

    public void setUserId(Long[] userId) {
        this.userId = userId;
    }

    public Long getAbonnementId() {
        return abonnementId;
    }

    public void setAbonnementId(Long abonnementId) {
        this.abonnementId = abonnementId;
    }
}
