package ua.softserveinc.tc.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;
import ua.softserveinc.tc.entity.PersonalDiscount;

@JsonInclude(Include.NON_NULL)
public class PersonalDiscountDTO implements Serializable {

  private Long id;
  private Integer value;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime startTime;
  @JsonFormat(pattern = "HH:mm")
  private LocalTime endTime;
  private Boolean active;
  private UserDto user;

  public PersonalDiscountDTO() {
  }

  public PersonalDiscountDTO(PersonalDiscount discount) {
    this.id = discount.getId();
    this.value = discount.getValue();
    this.startTime = discount.getStartTime();
    this.endTime = discount.getEndTime();
    this.active = discount.getActive();
    this.user = new UserDto(discount.getUser());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
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

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
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
        Objects.equals(value, that.value) &&
        Objects.equals(startTime, that.startTime) &&
        Objects.equals(endTime, that.endTime) &&
        Objects.equals(active, that.active) &&
        Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value, startTime, endTime, active, user);
  }
}
