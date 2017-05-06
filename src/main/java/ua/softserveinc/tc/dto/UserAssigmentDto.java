package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.SubscriptionAssignment;

import java.util.Map;

/**
 * Created by Ivan on 02.05.2017.
 */
public class UserAssigmentDto {
    private String user;
    private String abonnement;
    private int hours;
    private long hoursLeft;

    public UserAssigmentDto() {
    }

    public UserAssigmentDto(String firstName, String lastName, String abonnement, int hours, Long hoursUsed) {
        this.user = firstName + " " + lastName;
        this.abonnement = abonnement;
        this.hours = hours;
        this.hoursLeft = (hoursUsed == null) ? this.hours : this.hours * 60 - hoursUsed;
    }

    public UserAssigmentDto(Map.Entry<SubscriptionAssignment, Long> assignment) {
        this.user = assignment.getKey().getUser().getFullName();
        this.abonnement = assignment.getKey().getAbonnement().getName();
        this.hours = assignment.getKey().getAbonnement().getHour();
        this.hoursLeft = this.hours * 60 - assignment.getValue().intValue();
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAbonnement() {
        return abonnement;
    }

    public void setAbonnement(String abonnement) {
        this.abonnement = abonnement;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public long getHoursLeft() {
        return hoursLeft;
    }

    public void setHoursLeft(long hoursLeft) {
        this.hoursLeft = hoursLeft;
    }

    @Override
    public String toString() {
        return "UserAssigmentDto{" +
                "user='" + user + '\'' +
                ", abonnement='" + abonnement + '\'' +
                ", hours=" + hours +
                ", hoursLeft=" + hoursLeft +
                '}';
    }
}
