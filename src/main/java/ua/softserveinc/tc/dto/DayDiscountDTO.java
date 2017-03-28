package ua.softserveinc.tc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import ua.softserveinc.tc.entity.DayDiscount;

@JsonInclude(Include.NON_NULL)
public class DayDiscountDTO implements Serializable {

  private Long id;
  private String reason;
  private Integer value;
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate startDate;
  @JsonFormat(pattern = "dd-MM-yyyy")
  private LocalDate endDate;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime endTime;
  private Boolean active;

  public DayDiscountDTO() {
  }

  public DayDiscountDTO(DayDiscount dayDiscount) {
    this.id = dayDiscount.getId();
    this.reason = dayDiscount.getReason();
    this.value = dayDiscount.getValue();
    this.startTime = dayDiscount.getStartTime();
    this.endTime = dayDiscount.getEndTime();
    this.startDate = dayDiscount.getStartDate();
    this.endDate = dayDiscount.getEndDate();
    this.active = dayDiscount.getActive();
  }

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

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
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
    DayDiscountDTO that = (DayDiscountDTO) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(reason, that.reason) &&
        Objects.equals(value, that.value) &&
        Objects.equals(startDate, that.startDate) &&
        Objects.equals(endDate, that.endDate) &&
        Objects.equals(startTime, that.startTime) &&
        Objects.equals(endTime, that.endTime) &&
        Objects.equals(active, that.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason, value, startDate, endDate, startTime, endTime, active);
  }
}
