package ua.softserveinc.tc.dto;

public class UserAbonnementDto {
    Long user_id;
    Long abonnement_id;

    public UserAbonnementDto() {

    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getAbonnement_id() {
        return abonnement_id;
    }

    public void setAbonnement_id(Long abonnement_id) {
        this.abonnement_id = abonnement_id;
    }
}
