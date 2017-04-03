package ua.softserveinc.tc.entity;

import java.time.LocalDate;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.DiscountConstants;
import ua.softserveinc.tc.dto.DayDiscountDTO;
import ua.softserveinc.tc.entity.converter.LocalDateAttributeConverter;
import ua.softserveinc.tc.entity.converter.LocalTimeAttributeConverter;

@Entity
@Table(name = DiscountConstants.Entity.TABLE_NAME_DAYSDISCOUNTS)
public class DayDiscount {

  @Id
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = DiscountConstants.Entity.DAY_DISCOUNT_ID, nullable = false)
  private Long id;

  @Column(name = DiscountConstants.Entity.DISCOUNT_REASON, nullable = false)
  @Size(min = 3, max = 255)
  private String reason;

  @Column(name = DiscountConstants.Entity.DISCOUNT_VALUE, nullable = false)
  @Max(value = 100)
  @Min(value = 1)
  private int value;

  @Column(name = DiscountConstants.Entity.DISCOUNT_START_DATE, nullable = false)
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate startDate;

  @Column(name = DiscountConstants.Entity.DISCOUNT_END_DATE, nullable = false)
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate endDate;

  @Column(name = DiscountConstants.Entity.DISCOUNT_START_TIME, nullable = false)
  @Convert(converter = LocalTimeAttributeConverter.class)
  private LocalTime startTime;

  @Column(name = DiscountConstants.Entity.DISCOUNT_END_TIME, nullable = false)
  @Convert(converter = LocalTimeAttributeConverter.class)
  private LocalTime endTime;

  @Column(name = DiscountConstants.Entity.DISCOUNT_ACTIVE, nullable = false)
  private Boolean active;

  public DayDiscount() {

  }

  public DayDiscount(DayDiscountDTO dto) {
    if (dto.getId() != null) {
      this.id = dto.getId();
    }
    this.reason = dto.getReason();
    this.value = dto.getValue();
    this.startTime = dto.getStartTime();
    this.endTime = dto.getEndTime();
    this.startDate = dto.getStartDate();
    this.endDate = dto.getEndDate();
    this.active = dto.getActive();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public LocalTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DayDiscount discount = (DayDiscount) o;
    return value == discount.value &&
        Objects.equals(id, discount.id) &&
        Objects.equals(reason, discount.reason) &&
        Objects.equals(startDate, discount.startDate) &&
        Objects.equals(endDate, discount.endDate) &&
        Objects.equals(startTime, discount.startTime) &&
        Objects.equals(endTime, discount.endTime) &&
        Objects.equals(active, discount.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason, value, startDate, endDate, startTime, endTime, active);
  }
}
