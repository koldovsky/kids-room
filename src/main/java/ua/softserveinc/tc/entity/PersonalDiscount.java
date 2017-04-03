package ua.softserveinc.tc.entity;

import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.ChildConstants;
import ua.softserveinc.tc.constants.DiscountConstants;
import ua.softserveinc.tc.dto.PersonalDiscountDTO;
import ua.softserveinc.tc.dto.UserDto;
import ua.softserveinc.tc.entity.converter.LocalTimeAttributeConverter;

@Entity
@Table(name = DiscountConstants.Entity.TABLE_NAME_PERSONALDISCOUNTS)
public class PersonalDiscount {

  @Id
  @GenericGenerator(name = "generator", strategy = "increment")
  @GeneratedValue(generator = "generator")
  @Column(name = DiscountConstants.Entity.DAY_DISCOUNT_ID, nullable = false)
  private Long id;

  @Column(name = DiscountConstants.Entity.DISCOUNT_VALUE, nullable = false)
  @Max(value = 100)
  @Min(value = 1)
  private int value;

  @Column(name = DiscountConstants.Entity.DISCOUNT_START_TIME, nullable = false)
  @Convert(converter = LocalTimeAttributeConverter.class)
  private LocalTime startTime;

  @Column(name = DiscountConstants.Entity.DISCOUNT_END_TIME, nullable = false)
  @Convert(converter = LocalTimeAttributeConverter.class)
  private LocalTime endTime;

  @Column(name = DiscountConstants.Entity.DISCOUNT_ACTIVE, nullable = false)
  private Boolean active;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = ChildConstants.ID_PARENT)
  private User user;

  public PersonalDiscount() {
  }

  public PersonalDiscount(PersonalDiscountDTO dto) {
    if(dto.getId() != null){
      this.id = dto.getId();
    }
    this.value = dto.getValue();
    this.startTime = dto.getStartTime();
    this.endTime = dto.getEndTime();
    this.active = dto.getActive();
    this.setUser(dto.getUser());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setUser(UserDto userDto) {
    User user = this.getUser();
    user.setId(userDto.getId());
    user.setFirstName(userDto.getFirstName());
    user.setLastName(userDto.getLastName());
    user.setEmail(userDto.getEmail());
    user.setPhoneNumber(userDto.getPhoneNumber());
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
