package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.DayDiscountDTO;

import java.time.LocalTime;
import java.util.Objects;

public class Discount {
    private String reason;
    private int value;
    private LocalTime startTime;
    private LocalTime endTime;

    public Discount(String input) {
        String[] array = input.split("\\+");

        this.reason = array[0];
        this.value = Integer.parseInt(array[1]);
        this.startTime = LocalTime.parse(array[2]);
        this.endTime = LocalTime.parse(array[3]);
    }

    public Discount(String reason, int value, LocalTime startTime, LocalTime endTime) {
        this.reason = reason;
        this.value = value;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Discount(DayDiscountDTO dto) {
        this.reason = dto.getReason();
        this.value = dto.getValue();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public int getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean containPeriod(LocalTime startPeriodTime, LocalTime endPeriodTime) {
        return startTime.isBefore(endPeriodTime) && endTime.isAfter(startPeriodTime);
    }

    @Override
    public String toString() {
        return new StringBuilder().append(value).append("% - ")
                .append(reason).append(": ")
                .append(startTime).append("-")
                .append(endTime).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return value == discount.value &&
                Objects.equals(reason, discount.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reason, value);
    }
}
