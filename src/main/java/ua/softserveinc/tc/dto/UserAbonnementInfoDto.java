package ua.softserveinc.tc.dto;

public class UserAbonnementInfoDto {
    private String name;
    private long price;
    private int hour;
    private long remainingTime;

    public UserAbonnementInfoDto() {
    }

    public UserAbonnementInfoDto(SubscriptionsUsedHoursDto subscriptionsUsedHoursDto) {
        this.name = subscriptionsUsedHoursDto.getAssignmentDto().getAbonnement().getName();
        this.price = subscriptionsUsedHoursDto.getAssignmentDto().getAbonnement().getPrice();
        this.hour = subscriptionsUsedHoursDto.getAssignmentDto().getAbonnement().getHour();
        this.remainingTime = subscriptionsUsedHoursDto.getMinutesLeft() / 60;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}
