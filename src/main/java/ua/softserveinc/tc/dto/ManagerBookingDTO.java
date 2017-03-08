package ua.softserveinc.tc.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.entity.BookingState;

//TODO-VL Rename this class to BookingDto when all usage of previous BookingDto will be replaced
@JsonInclude(Include.NON_NULL)
public class ManagerBookingDTO implements Serializable {

  private Long id;
  private Long idChild;
  private String kidName;
  private String comment;
  private String startTime;
  private String endTime;
  private Long startTimeMillis;
  private Long endTimeMillis;
  private Long durationBooking;

  private BookingState bookingState;

  private ManagerBookingDTO() {
  }

  public static class Builder {

    private DateFormat timeFormat = new SimpleDateFormat(DateConstants.TIME_FORMAT);

    private ManagerBookingDTO myDTO;

    public Builder() {
      myDTO = new ManagerBookingDTO();
    }

    public Builder withId(Long id) {
      this.myDTO.setId(id);
      return this;
    }

    public Builder withIdChild(Long id) {
      this.myDTO.setIdChild(id);
      return this;
    }

    public Builder withKidName(String name){
      this.myDTO.setKidName(name);
      return this;
    }

    public Builder withComment(String comment){
      this.myDTO.setComment(comment);
      return this;
    }

    public Builder withStartTime(Date startTime){
      this.myDTO.setStartTime(timeFormat.format(startTime));
      return this;
    }

    public Builder withEndTime(Date endTime){
      this.myDTO.setEndTime(timeFormat.format(endTime));
      return this;
    }

    public Builder withStartTimeMillis(Date startTimeMillis) {
      this.myDTO.setStartTimeMillis(startTimeMillis.getTime());
      return this;
    }

    public Builder withEndTimeMillis(Date endTimeMillis) {
      this.myDTO.setEndTimeMillis(endTimeMillis.getTime());
      return this;
    }

    public Builder withDurationBooking(Long durationBooking){
      this.myDTO.setDurationBooking(durationBooking);
      return this;
    }

    public Builder withBookingState(BookingState bookingState){
      this.myDTO.setBookingState(bookingState);
      return this;
    }

    public ManagerBookingDTO build() {
      return this.myDTO;
    }

  }

  //Getters and setters for DTO
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getIdChild() {
    return idChild;
  }

  public void setIdChild(Long idChild) {
    this.idChild = idChild;
  }

  public String getKidName() {
    return kidName;
  }

  public void setKidName(String kidName) {
    this.kidName = kidName;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public BookingState getBookingState() {
    return bookingState;
  }

  public void setBookingState(BookingState bookingState) {
    this.bookingState = bookingState;
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

  public Long getDurationBooking() {
    return durationBooking;
  }

  public void setDurationBooking(Long durationBooking) {
    this.durationBooking = durationBooking;
  }
}
