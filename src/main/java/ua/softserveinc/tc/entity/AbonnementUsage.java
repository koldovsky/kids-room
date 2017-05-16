package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.AbonnementConstants;
import ua.softserveinc.tc.entity.converter.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "abbonement_usages")
public class AbonnementUsage {
    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    private Long idUsage;

    @ManyToOne
    @JoinColumn(name = AbonnementConstants.Entity.SUBSCRIPTION_ID)
    private SubscriptionAssignment assignment;

    @Column
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime timestamp;

    @Column(name = "used_minutes")
    private long usedMinutes;

    public AbonnementUsage() {
    }



    public AbonnementUsage(SubscriptionAssignment assignment, LocalDateTime timestamp, long usedMinutes) {
        this.assignment = assignment;
        this.timestamp = timestamp;
        this.usedMinutes = usedMinutes;
    }

    public Long getIdUsage() {
        return idUsage;
    }

    public void setIdUsage(Long idUsage) {
        this.idUsage = idUsage;
    }

    public SubscriptionAssignment getAssignment() {
        return assignment;
    }

    public void setAssignment(SubscriptionAssignment assignment) {
        this.assignment = assignment;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public long getUsedMinutes() {
        return usedMinutes;
    }

    public void setUsedMinutes(long usedMinutes) {
        this.usedMinutes = usedMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbonnementUsage that = (AbonnementUsage) o;
        return usedMinutes == that.usedMinutes &&
                Objects.equals(idUsage, that.idUsage) &&
                Objects.equals(assignment, that.assignment) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsage, assignment, timestamp, usedMinutes);
    }
}
