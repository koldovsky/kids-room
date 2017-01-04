package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.entity.*;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Петришак on 14.05.2016.
 */


public class BookingDto implements Serializable {

    private Long id;
    private String date;
    private String endDate;
    private String startTime;
    private String endTime;
    private Long startTimeMillis;
    private Long endTimeMillis;
    private Long durationBooking;
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
        id = newBookingDto.id;
        date = newBookingDto.date;
        endDate = newBookingDto.endDate;
        startTime = newBookingDto.startTime;
        endTime = newBookingDto.endTime;
        startTimeMillis = newBookingDto.startTimeMillis;
        endTimeMillis = newBookingDto.endTimeMillis;
        if (endTimeMillis != null && startTimeMillis != null) {
            durationBooking = endTimeMillis - startTimeMillis;
        } else if (dateEndTime != null && dateStartTime != null) {
            durationBooking = dateEndTime.getTime() - dateStartTime.getTime();
        } else {
            durationBooking = 0L;
        }
        kidName = newBookingDto.kidName;
        roomName = newBookingDto.roomName;
        duration = newBookingDto.duration;
        idChild = newBookingDto.idChild;
        durationLong = newBookingDto.durationLong;
        bookingState = newBookingDto.bookingState;
        comment = newBookingDto.comment;
        recurrentId = newBookingDto.recurrentId;
        userId = newBookingDto.userId;
        kidId = newBookingDto.kidId;
        roomId = newBookingDto.roomId;
        daysOfWeek = newBookingDto.daysOfWeek;
        weekDays = newBookingDto.weekDays;
        child = newBookingDto.child;
        user = newBookingDto.user;
        room = newBookingDto.room;
    }

    public BookingDto(Booking booking) {
        DateFormat df = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
        date = df.format(booking.getBookingStartTime());
        endDate = df.format(booking.getBookingEndTime());
        df = new SimpleDateFormat(DateConstants.TIME_FORMAT);
        startTime = df.format(booking.getBookingStartTime());
        endTime = df.format(booking.getBookingEndTime());
        startTimeMillis = booking.getBookingStartTime().getTime();
        endTimeMillis = booking.getBookingEndTime().getTime();
        kidName = booking.getChild().getFullName();
        roomName = booking.getRoom().getAddress();
        durationBooking = endTimeMillis - startTimeMillis;
        duration = booking.formatDuration();
        sum = booking.getSum();
        id = booking.getIdBook();
        bookingState = booking.getBookingState();
        durationLong = booking.getDuration();
        idChild = booking.getChild().getId();
        comment = booking.getComment();
        recurrentId = booking.getRecurrentId();
    }


    public static BookingDto getBookingDto(final List<Booking> listOfRecurrentBooking,
                                           final Set<Integer> weekDays) {
        Booking recurrentStartDay = listOfRecurrentBooking.get(0);
        Booking recurrentEndDay = listOfRecurrentBooking.get(
                listOfRecurrentBooking.size() - 1);
        recurrentStartDay.setBookingEndTime(recurrentEndDay.getBookingEndTime());
        BookingDto recurrentBookingDto = new BookingDto(recurrentStartDay);
        recurrentBookingDto.setWeekDays(weekDays);
        return recurrentBookingDto;
    }

    public void setWeekDays(Set<Integer> weekDays) {
        this.weekDays = weekDays;

    }

    public Long getDurtionLong() {
        return durationLong;
    }

    public void setDurtionLong(Long durtionLong) {
        this.durationLong = durtionLong;
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
        if (durationLong == null) {
            booking.setDuration(dateEndTime.getTime() - dateStartTime.getTime());
        } else {
            booking.setDuration(durationLong);
        }
        if (sum == null) {
            booking.setSum(0L);
        }
        return booking;
    }

    /**
     * Creates new object BookingDto with the same data as 'this' object
     * and set start and end time. Returns resulting object.
     *
     * @param dates the dates of start and end times
     * @return resulting list of BookingDto
     */
    public BookingDto getNewBookingDto(Date[] dates) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(id);
        bookingDto.setRoom(room);
        bookingDto.setUser(user);
        bookingDto.setChild(child);
        bookingDto.setRecurrentId(recurrentId);
        bookingDto.setDateStartTime(dates[0]);
        bookingDto.setDateEndTime(dates[1]);
        bookingDto.setStartTime(DateConstants.DATE_FORMAT_OBJECT.format(dates[0]));
        bookingDto.setEndTime(DateConstants.DATE_FORMAT_OBJECT.format(dates[1]));
        bookingDto.setBookingState(BookingState.BOOKED);
        bookingDto.setSum(0L);
        bookingDto.setDurationLong(dates[1].getTime() - dates[0].getTime());
        bookingDto.setComment(comment);
        bookingDto.setIdChild(idChild);
        bookingDto.setKidId(kidId);
        bookingDto.setKidName(child.getFullName());
        return bookingDto;
    }

    /**
     * Receives list of BookingsDto and set for each element appropriate id
     * from list of Booking than return resulting list of BookingDto
     *
     * @param dtos     list of BookingDto
     * @param bookings list of Booking
     * @return list of set BookingDto
     */
    public static List<BookingDto> setIdToListOfBookingDto(List<BookingDto> dtos, List<Booking> bookings) {
        Iterator<BookingDto> iteratorDto = dtos.iterator();
        Iterator<Booking> iteratorBook = bookings.iterator();
        while (iteratorDto.hasNext() && iteratorBook.hasNext()) {
            iteratorDto.next().setId(iteratorBook.next().getIdBook());
        }
        return dtos;
    }

    /**
     * Receives list of BookingDto and returns list of Bookings.
     *
     * @param dtos given list of BookingDto
     * @return list of appropriate Bookings
     */
    public static List<Booking> getListOfBookingObjects(List<BookingDto> dtos) {
        List<Booking> listOfBookings = new ArrayList<>();
        dtos.forEach(singleDto -> listOfBookings.add(singleDto.getBookingObject()));
        return listOfBookings;
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
        DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
        this.startTime = df.format(dateStartTime);

    }

    public Date getDateEndTime() {
        return dateEndTime;
    }

    public void setDateEndTime(Date dateEndTime) {
        this.dateEndTime = dateEndTime;
        DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
        this.endTime = df.format(dateEndTime);
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

    public void setStartTimeMillis(Long startTime) {
        this.startTimeMillis = startTime;
    }

    public Long getStartTimeMillis() {
        return startTimeMillis;
    }

    public void setEndTimeMillis(Long endTime) {
        this.endTimeMillis = endTime;
    }

    public Long getEndTimeMillis() {
        return endTimeMillis;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(Date startTime) {
        DateFormat df = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
        this.startTime = df.format(startTime);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        DateFormat df = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
        this.endTime = df.format(endTime);
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

    public Set<Integer> getWeekDays() {
        return weekDays;
    }

    public Long getDurationBooking() {
        return durationBooking;
    }

    public void setDurationBooking(Long durationBooking) {
        this.durationBooking = durationBooking;
    }
}
