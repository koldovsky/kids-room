package ua.softserveinc.tc.util;

import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;

import java.time.LocalTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Discount {
    public static String PERSONAL_DISCOUNT_REASON = "Personal discount";

    private String reason;
    private int value;
    private LocalTime startTime;
    private LocalTime endTime;

    public Discount(int value) {
        this.value = value;
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

    public Discount(PersonalDiscountDTO dto) {
        this.reason = Discount.PERSONAL_DISCOUNT_REASON;
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

    public static LocalTime differenceBetweenTwoTimes(LocalTime time1, LocalTime time2) {
        return LocalTime.ofSecondOfDay(SECONDS.between(time1, time2));
    }

    public static LocalTime addTwoTimes(LocalTime time1, LocalTime time2) {
        return time1.plusSeconds(time2.toSecondOfDay());
    }

    @Override
    public String toString() {
        return String.valueOf(value) + "% - "
                + Discount.differenceBetweenTwoTimes(startTime, endTime);
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
