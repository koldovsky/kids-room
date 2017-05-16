package ua.softserveinc.tc.dto;

public class UserAbonnementsDto {
    private Long userId;
    private Long[] abonnementIds;

    public UserAbonnementsDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long[] getAbonnementIds() {
        return abonnementIds;
    }

    public void setAbonnementIds(Long[] abonnementIds) {
        this.abonnementIds = abonnementIds;
    }
}
