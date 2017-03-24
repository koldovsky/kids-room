package ua.softserveinc.tc.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.sql.Time;
import java.util.Objects;
import ua.softserveinc.tc.entity.User;

@JsonInclude(Include.NON_NULL)
public class PersonalDiscountDTO implements Serializable {

  private Long id;
  private String reason;
  private Integer value;
  private Time startTime;
  private Time endTime;
  private Boolean active;
  private User user;

  public PersonalDiscountDTO(){

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
    PersonalDiscountDTO that = (PersonalDiscountDTO) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(reason, that.reason) &&
        Objects.equals(value, that.value) &&
        Objects.equals(startTime, that.startTime) &&
        Objects.equals(endTime, that.endTime) &&
        Objects.equals(active, that.active) &&
        Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, reason, value, startTime, endTime, active, user);
  }
}
