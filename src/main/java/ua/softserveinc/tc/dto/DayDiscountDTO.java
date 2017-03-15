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

  private DayDiscountDTO() {
  }

  public static class Builder {

    private DayDiscountDTO myDTO;

    public Builder() {
      this.myDTO = new DayDiscountDTO();
    }

    public Builder withId(Long id) {
      this.myDTO.setId(id);
      return this;
    }

    public Builder withReason(String reason) {
      this.myDTO.setReason(reason);
      return this;
    }

    public Builder withValue(Integer value) {
      this.myDTO.setValue(value);
      return this;
    }

    public Builder withStartDate(Date startDate) {
      this.myDTO.setStartDate(startDate);
      return this;
    }

    public Builder withEndDate(Date endDate) {
      this.myDTO.setEndDate(endDate);
      return this;
    }

    public DayDiscountDTO build() {
      return this.myDTO;
    }

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
        Objects.equals(endDate, that.endDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason, value, startDate, endDate);
  }
}
