package ua.softserveinc.tc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ua.softserveinc.tc.entity.SubscriptionAssignment;

import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionAssignmentDto implements Serializable {

    private Long id;
    private UserDto user;
    private AbonnementDto abonnement;
    private Boolean valid;

    public SubscriptionAssignmentDto() {
    }

    public SubscriptionAssignmentDto(SubscriptionAssignment assignment) {
        this.id = assignment.getId();
        this.user = new UserDto(assignment.getUser());
        this.abonnement = new AbonnementDto(assignment.getAbonnement());
        this.valid = assignment.isValid();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public AbonnementDto getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(AbonnementDto abonnement) {
        this.abonnement = abonnement;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubscriptionAssignmentDto that = (SubscriptionAssignmentDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(abonnement, that.abonnement) &&
                Objects.equals(valid, that.valid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, abonnement, valid);
    }
}
