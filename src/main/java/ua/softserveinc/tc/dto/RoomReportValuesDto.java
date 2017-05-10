package ua.softserveinc.tc.dto;

import org.joda.time.Minutes;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.util.CurrencyConverter;
import ua.softserveinc.tc.util.DateUtil;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class RoomReportValuesDto {
    private User user;
    private Duration spentHours;
    private Duration abonnementHours;
    private long sum;

    public RoomReportValuesDto() {
    }

    public RoomReportValuesDto(Booking booking) {
        user = booking.getUser();
        spentHours = Duration.between(DateUtil.dateToLocalTime(booking.getBookingStartTime()),
                DateUtil.dateToLocalTime(booking.getBookingEndTime()));
        Optional<String> abonnementTime = Optional.ofNullable(booking.getAbonnements());
        abonnementHours = Duration.between(LocalTime.MIN,
                LocalTime.parse(abonnementTime.orElse("00:00")));
        sum = booking.getSum();
    }

    public void addReports(RoomReportValuesDto reportValuesDto) {
        setSpentHours(this.spentHours.plus(reportValuesDto.getSpentHours()));
        setAbonnementHours(this.abonnementHours.plus(reportValuesDto.getAbonnementHours()));
        setSum(this.sum + reportValuesDto.getSum());
    }

    public Duration getSpentHours() {
        return spentHours;
    }

    public void setSpentHours(Duration spentHours) {
        this.spentHours = spentHours;
    }

    public Duration getAbonnementHours() {
        return abonnementHours;
    }

    public void setAbonnementHours(Duration abonnementHours) {
        this.abonnementHours = abonnementHours;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStringSum() {
        return CurrencyConverter.getInstance().convertSingle(sum);
    }

    public String getStringAbonnementHours() {
        return durationToString(abonnementHours);
    }

    public String getStringSpendHours() {
        return durationToString(spentHours);
    }

    private String durationToString(Duration duration) {
        return new StringBuilder().append(duration.toHours())
                .append(":")
                .append(duration.toMinutes() % 60 < 10
                        ? "0" + duration.toMinutes() % 60 : duration.toMinutes() % 60)
                .append(":00").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomReportValuesDto that = (RoomReportValuesDto) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
