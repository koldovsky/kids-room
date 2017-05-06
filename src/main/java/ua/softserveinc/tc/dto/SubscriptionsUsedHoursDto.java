package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.SubscriptionAssignment;

/**
 * Created by Ivan on 02.05.2017.
 */
public class SubscriptionsUsedHoursDto {
    private SubscriptionAssignmentDto assignmentDto;
    private long minutesLeft;

    public SubscriptionsUsedHoursDto() {
    }

    public SubscriptionsUsedHoursDto(SubscriptionAssignment assignment, Long minutesLeft) {
        this.assignmentDto = new SubscriptionAssignmentDto(assignment);
        this.minutesLeft = this.assignmentDto.getAbonnement().getHour() * 60;
        this.minutesLeft -= (minutesLeft == null) ? 0L : minutesLeft;
    }

    public SubscriptionAssignmentDto getAssignmentDto() {
        return assignmentDto;
    }

    public void setAssignmentDto(SubscriptionAssignmentDto assignmentDto) {
        this.assignmentDto = assignmentDto;
    }

    public long getMinutesLeft() {
        return minutesLeft;
    }

    public int getAbonnementsMinutes() {
        return assignmentDto.getAbonnement().getHour() * 60;
    }

    public void setMinutesLeft(long hoursLeft) {
        this.minutesLeft = hoursLeft;
    }

    public boolean isActive() {
        return assignmentDto.getValid();
    }

    public SubscriptionAssignment getSubscriptionAssignment() {
        return new SubscriptionAssignment(this.assignmentDto);
    }
}
