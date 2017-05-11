package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.AbonnementUsage;
import ua.softserveinc.tc.entity.SubscriptionAssignment;
import ua.softserveinc.tc.util.DateUtil;

import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

public class UserAssigmentDto {
    private String user;
    private String email;
    private String abonnement;
    private int hours;
    private long minutesLeft;
    private String leftTime;

    public UserAssigmentDto() {
    }

    public UserAssigmentDto(String userName, String email, String abonnement, int hours, Long minutesLeft) {
        this.user = userName;
        this.email = email;
        this.abonnement = abonnement;
        this.hours = hours;
        this.minutesLeft = minutesLeft;
        this.leftTime = transformMinutesToLocalTimeString();
    }

    public UserAssigmentDto(Map.Entry<SubscriptionAssignment, Long> assignment) {
        this.user = assignment.getKey().getUser().getFullName();
        this.abonnement = assignment.getKey().getAbonnement().getName();
        this.hours = assignment.getKey().getAbonnement().getHour();
        this.minutesLeft = this.hours * 60 - assignment.getValue().intValue();
    }

    public UserAssigmentDto(SubscriptionAssignment assignment) {
        this.user = assignment.getUser().getFullName();
        this.email = assignment.getUser().getEmail();
        this.abonnement = assignment.getAbonnement().getName();
        this.hours = assignment.getAbonnement().getHour();
        this.minutesLeft = assignment.getAbonnementUsages().isEmpty() ? this.hours * 60
                : this.hours * 60 - assignment.getAbonnementUsages().stream()
                    .mapToLong(AbonnementUsage::getUsedMinutes).sum();
        this.leftTime = transformMinutesToLocalTimeString();
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

    public long getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(long minutesLeft) {
        this.minutesLeft = minutesLeft;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String transformMinutesToLocalTimeString() {
        return LocalTime.ofSecondOfDay(60 * minutesLeft).toString();
    }

    public String getLeftTime() {
        return leftTime;
    }

    public void setLeftTime(String leftTime) {
        this.leftTime = leftTime;
    }

    @Override
    public String toString() {
        return "UserAssigmentDto{" +
                "user='" + user + '\'' +
                ", email='" + email + '\'' +
                ", abonnement='" + abonnement + '\'' +
                ", hours=" + hours +
                ", hoursLeft=" + minutesLeft +
                '}';
    }
}
