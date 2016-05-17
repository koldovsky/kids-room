package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.entity.Booking;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Петришак on 14.05.2016.
 */
public class BookingDTO  implements BaseDTO {

    String start;
    Long id;

    //Нестор: я зайшов пододавати поля і методи, ламати нічого не буду :)

    private String date;
    private String startTime;
    private String endTime;
    private String kidName;
    private String roomName;
    private String duration;
    private int sum;



   public BookingDTO(String start){
        this.start=start;

    }
    public BookingDTO(String start, Long id){
        this.start=start;
        this.id=id;
    }

    public BookingDTO(Booking booking){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.date = df.format(booking.getBookingStartTime());

        df = new SimpleDateFormat("HH:mm");
        this.startTime = df.format(booking.getBookingStartTime());
        this.endTime = df.format(booking.getBookingEndTime());

        this.kidName = booking.getIdChild().getFullName();
        this.roomName = booking.getIdRoom().getAddress();
        this.duration = booking.getDuration();
        this.sum = booking.getSum();

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

}
