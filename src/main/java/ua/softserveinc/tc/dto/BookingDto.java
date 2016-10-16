package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.entity.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * Created by Петришак on 14.05.2016.
 */


public class BookingDto implements Serializable{

    private Long id;
    private String date;
    private String endDate;
    private String startTime;
    private String endTime;
    private String kidName;
    private String roomName;
    private String duration;
    private Long idChild;
    private Long sum;
    private Long durationLong;
    private BookingState bookingState;
    private String comment;
    private Long recurrentId;

    private Long userId;
    private Long kidId;
    private Long roomId;

    private String daysOfWeek;
    private Set<Integer> weekDays;

    private transient Child child;
    private transient User user;
    private transient Room room;

    private transient Date dateStartTime;
    private transient Date dateEndTime;

    public BookingDto() {
    }

    public BookingDto(BookingDto newBookingDto) {
        this.date=newBookingDto.date;
        this.endDate=newBookingDto.endDate;
        this.startTime=newBookingDto.startTime;
        this.endTime=newBookingDto.endTime;
        this.kidName=newBookingDto.kidName;
        this.roomName=newBookingDto.roomName;
        this.duration=newBookingDto.duration;
        this.idChild=newBookingDto.idChild;
        this.durationLong=newBookingDto.durationLong;
        this.bookingState=newBookingDto.bookingState;
        this.comment=newBookingDto.comment;
        this.recurrentId=newBookingDto.recurrentId;
        this.userId=newBookingDto.userId;
        this.kidId=newBookingDto.kidId;
        this.roomId=newBookingDto.roomId;
        this.daysOfWeek=newBookingDto.daysOfWeek;
        this.weekDays=newBookingDto.weekDays;
        this.child=newBookingDto.child;
        this.user=newBookingDto.user;
        this.room=newBookingDto.room;
    }

    public BookingDto(Booking booking) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        this.date = df.format(booking.getBookingStartTime());
        this.endDate = df.format(booking.getBookingEndTime());
        df = new SimpleDateFormat(DateConstants.TIME_FORMAT);
        this.startTime = df.format(booking.getBookingStartTime());
        this.endTime = df.format(booking.getBookingEndTime());
        this.kidName = booking.getChild().getFullName();
        this.roomName = booking.getRoom().getAddress();
        this.duration = booking.formatDuration();
        this.sum = booking.getSum();
        this.id = booking.getIdBook();
        this.bookingState = booking.getBookingState();
        this.durationLong = booking.getDuration();
        this.idChild = booking.getChild().getId();
        this.comment = booking.getComment();
        this.recurrentId = booking.getRecurrentId();
    }


    public static BookingDto getBookingDto(final List<Booking> listOfRecurrentBooking, final Set<Integer> weekDays) {
        Booking recurrentStartDay = listOfRecurrentBooking.get(0);
        Booking recurrentEndDay = listOfRecurrentBooking.get(listOfRecurrentBooking.size() - 1);
        recurrentStartDay.setBookingEndTime(recurrentEndDay.getBookingEndTime());
        BookingDto recurrentBookingDto = new BookingDto(recurrentStartDay);
        recurrentBookingDto.setWeekDays(weekDays);
        return recurrentBookingDto;
    }

    public void setWeekDays(Set<Integer> weekDays)
    {
        this.weekDays=weekDays;

    }

    public Booking getBookingObject() {
        Booking booking = new Booking();
        booking.setBookingStartTime(dateStartTime);
        booking.setBookingEndTime(dateEndTime);
        booking.setComment(comment);
        booking.setRoom(room);
        booking.setChild(child);
        booking.setUser(user);
        booking.setBookingState(bookingState);
        booking.setRecurrentId(recurrentId);
        long duraion = booking.getBookingEndTime().getTime() -
                booking.getBookingStartTime().getTime();
        booking.setDuration(duraion);
        return booking;
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

    public Date getDateStartTime() {
        return dateStartTime;
    }

    public void setDateStartTime(Date dateStartTime) {
        this.dateStartTime = dateStartTime;
    }

    public Date getDateEndTime() {
        return dateEndTime;
    }

    public void setDateEndTime(Date dateEndTime) {
        this.dateEndTime = dateEndTime;
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

    public Long getIdChild() {
        return idChild;
    }

    public void setIdChild(Long idChild) {
        this.idChild = idChild;
    }

    public Long getRecurrentId() {
        return recurrentId;
    }

    public void setRecurrentId(Long recurrentId) {
        this.recurrentId = recurrentId;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }
    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Set<Integer> getWeekDays(){
        return weekDays;
    }

}
