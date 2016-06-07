package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.constants.ModelConstants.DateConst;
import ua.softserveinc.tc.entity.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Петришак on 14.05.2016.
 */

public class BookingDto {

    private Long id;
    private String date;
    private String startTime;
    private String endTime;
    private String kidName;
    private String roomName;
    private String duration;
    private Long sum;
    private Long durationLong;
    private BookingState bookingState;
    private String comment;

    private Long userId;
    private Long kidId;
    private Long roomId;

    transient private Child child;
    transient private User user;
    transient private Room room;

    public BookingDto() {
        this.id=id;
        this.startTime=startTime;
    }

    public BookingDto(Booking booking){
        DateFormat df = new SimpleDateFormat(DateConst.DAY_MOUNTH_YEAR);
        this.date = df.format(booking.getBookingStartTime());

        df = new SimpleDateFormat(DateConst.TIME_FORMAT);
        this.startTime = df.format(booking.getBookingStartTime());
        this.endTime = df.format(booking.getBookingEndTime());

        this.kidName = booking.getIdChild().getFullName();
        this.roomName = booking.getIdRoom().getAddress();
        this.duration = booking.formatDuration();
        this.sum = booking.getSum();
        this.id = booking.getIdBook();
        this.bookingState = booking.getBookingState();
        this.durationLong = booking.getDuration();

    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Booking getBookingObject(){
        DateFormat dateFormat = new SimpleDateFormat(DateConst.DATE_AND_TIME_FORMAT);
        Booking booking = new Booking();
        try {
            booking.setBookingStartTime(dateFormat.parse(date + " " + startTime));
            booking.setBookingEndTime(dateFormat.parse(date + " " + endTime));
        }
        catch(ParseException pe){
            pe.printStackTrace();
            //TODO: throw another exception
        }

        booking.setComment(comment);
        booking.setIdRoom(room);
        booking.setIdChild(child);
        booking.setIdUser(user);
        return booking;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }

    public Long getDurationLong() {
        return durationLong;
    }

    public void setDurationLong(Long durationLong) {
        this.durationLong = durationLong;
    }

    public Long getKidId() {
        return kidId;
    }

    public void setKidId(Long kidId) {
        this.kidId = kidId;
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

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDurtionLong() {
        return durationLong;
    }

    public void setDurtionLong(Long durtionLong) {
        this.durationLong = durtionLong;
    }
}
