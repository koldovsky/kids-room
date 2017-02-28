package ua.softserveinc.tc.dto;


import java.io.Serializable;

public class ManagerBookingDTO implements Serializable {

  private String name;
  private Long startTimeMillis;
  private Long endTimeMillis;

  private ManagerBookingDTO() {
  }

  public static class Builder {

    private ManagerBookingDTO myDTO;

    public Builder() {
      myDTO = new ManagerBookingDTO();
    }

    public Builder startTimeMillis(Long startTimeMillis) {
      this.myDTO.setStartTimeMillis(startTimeMillis);
      return this;
    }

    public Builder endTimeMillis(Long endTimeMillis) {
      this.myDTO.setEndTimeMillis(endTimeMillis);
      return this;
    }

    public ManagerBookingDTO build() {
      return this.myDTO;
    }

  }

  //Getters and setters for DTO

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getStartTimeMillis() {
    return startTimeMillis;
  }

  public void setStartTimeMillis(Long startTimeMillis) {
    this.startTimeMillis = startTimeMillis;
  }

  public Long getEndTimeMillis() {
    return endTimeMillis;
  }

  public void setEndTimeMillis(Long endTimeMillis) {
    this.endTimeMillis = endTimeMillis;
  }
}
