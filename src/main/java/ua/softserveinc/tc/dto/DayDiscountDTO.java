package ua.softserveinc.tc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonInclude(Include.NON_NULL)
public class DayDiscountDTO implements Serializable {

  private Long id;
  private String reason;
  private Integer value;
  private Date startDate;
  private Date endDate;
  private Boolean active;

  public DayDiscountDTO() {
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

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
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
        Objects.equals(active, that.active);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason, value, startDate, endDate, active);
  }
}
