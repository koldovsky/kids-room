package ua.softserveinc.tc.entity;

import java.sql.Time;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.DiscountConstants;

@Entity
@Table(name = DiscountConstants.Entity.TABLE_NAME_PERSONALDISCOUNTS)
public class PersonalDiscount {

  @Id
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = DiscountConstants.Entity.DAY_DISCOUNT_ID, nullable = false)
  private Long id;

  @Column(name = DiscountConstants.Entity.DISCOUNT_REASON, nullable = false)
  @NotNull
  @Size(min = 3, max = 255)
  private String reason;

  @Column(name = DiscountConstants.Entity.DISCOUNT_VALUE, nullable = false)
  @NotNull
  @Max(value = 100)
  @Min(value = 1)
  private int value;

  @Column(name = DiscountConstants.Entity.DISCOUNT_START_TIME, nullable = false)
  private Time startTime;

  @Column(name = DiscountConstants.Entity.DISCOUNT_END_TIME, nullable = false)
  private Time endTime;

  @Column(name = DiscountConstants.Entity.DISCOUNT_ACTIVE, nullable = false)
  @NotNull
  private Boolean active;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = ChildConstants.ID_PARENT)
  private User user;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public Time getStartTime() {
    return startTime;
  }

  public void setStartTime(Time startTime) {
    this.startTime = startTime;
  }

  public Time getEndTime() {
    return endTime;
  }

  public void setEndTime(Time endTime) {
    this.endTime = endTime;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalDiscount that = (PersonalDiscount) o;
    return value == that.value &&
        Objects.equals(id, that.id) &&
        Objects.equals(reason, that.reason) &&
        Objects.equals(startTime, that.startTime) &&
        Objects.equals(endTime, that.endTime) &&
        Objects.equals(active, that.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason, value, startTime, endTime, active);
  }
}
