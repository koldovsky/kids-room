package ua.softserveinc.tc.dto;

import ua.softserveinc.tc.constants.DateConstants;
import ua.softserveinc.tc.entity.User;
import ua.softserveinc.tc.entity.Room;
import ua.softserveinc.tc.entity.Child;
import ua.softserveinc.tc.entity.Booking;
import ua.softserveinc.tc.entity.BookingState;
import ua.softserveinc.tc.util.DateUtil;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


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
        dateEndTime = newBookingDto.dateEndTime;
        dateStartTime = newBookingDto.dateStartTime;
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
        DateFormat shortDateFormat = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
        DateFormat timeFormat = new SimpleDateFormat(DateConstants.TIME_FORMAT);

        duration = booking.formatDuration();
        sum = booking.getSum();
        id = booking.getIdBook();
        bookingState = booking.getBookingState();
        durationLong = booking.getDuration();
        comment = booking.getComment();
        recurrentId = booking.getRecurrentId();
        dateStartTime = booking.getBookingStartTime();
        dateEndTime = booking.getBookingEndTime();

        if (booking.getBookingStartTime() != null) {
            date = shortDateFormat.format(booking.getBookingStartTime());
            startTime = timeFormat.format(booking.getBookingStartTime());
        }
        if (booking.getBookingEndTime() != null) {
            endDate = shortDateFormat.format(booking.getBookingEndTime());
            endTime = timeFormat.format(booking.getBookingEndTime());
        }
        if (booking.getBookingStartTime() != null) {
            startTimeMillis = booking.getBookingStartTime().getTime();
        }
        if (booking.getBookingEndTime() != null) {
            endTimeMillis = booking.getBookingEndTime().getTime();
        }
        if (booking.getChild() != null) {
            kidName = booking.getChild().getFullName();
            idChild = booking.getChild().getId();
        }
        if (booking.getRoom() != null) {
            roomName = booking.getRoom().getAddress();
        }
        if (endTimeMillis != null && startTimeMillis != null) {
            durationBooking = endTimeMillis - startTimeMillis;
        } else {
            durationBooking = 0L;
        }
    }

    /**
     * Creates and returns the BookingDto object that contains start and end date
     * of recurrent period, and set weekDays.
     *
     * @param bookings the given list of bookings
     * @param weekDays the given set of weekDays
     * @return the bookingDto object
     */
    public static BookingDto getRecurrentBookingDto(List<Booking> bookings, Set<Integer> weekDays) {
        if (bookings == null || bookings.isEmpty()) {

            return new BookingDto();
        }
        Booking startDayBooking = bookings.get(0);
        Booking endDayBooking = bookings.get(bookings.size() - 1);
        BookingDto resultBookingDto = startDayBooking.getDto();

        resultBookingDto.setDateEndTime(endDayBooking.getBookingEndTime());
        resultBookingDto.setRightEndTime(endDayBooking.getBookingEndTime());
        resultBookingDto.setRightEndDate(endDayBooking.getBookingEndTime());
        resultBookingDto.setWeekDays(weekDays);

        return resultBookingDto;
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
        if (durationLong == null && dateEndTime != null && dateStartTime != null) {
            booking.setDuration(dateEndTime.getTime() - dateStartTime.getTime());
        } else {
            booking.setDuration(durationLong);
        }
        if (sum == null) {
            booking.setSum(0L);
        } else {
            booking.setSum(sum);
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
        if (dates == null) {

            return bookingDto;
        }
        bookingDto.setRoom(room);
        bookingDto.setUser(user);
        bookingDto.setChild(child);
        bookingDto.setRecurrentId(recurrentId);
        bookingDto.setBookingState(BookingState.BOOKED);
        bookingDto.setSum(0L);
        bookingDto.setComment(comment);
        bookingDto.setIdChild(idChild);
        bookingDto.setKidId(kidId);
        if (dates[0] != null) {
            bookingDto.setDateStartTime(dates[0]);
            bookingDto.setStartTime(DateConstants.DATE_FORMAT_OBJECT.format(dates[0]));
        }
        if (dates[1] != null) {
            bookingDto.setDateEndTime(dates[1]);
            bookingDto.setEndTime(DateConstants.DATE_FORMAT_OBJECT.format(dates[1]));
        }
        if (dates[0] != null && dates[1] != null) {
            bookingDto.setDurationLong(dates[1].getTime() - dates[0].getTime());
        } else {
            bookingDto.setDurationLong(0L);
        }
        if (child != null) {
            bookingDto.setKidName(child.getFullName());
        }

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
        if (dtos == null || bookings == null) {

            return Collections.emptyList();
        }
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
        if (dtos != null) {
            dtos.forEach(singleDto -> listOfBookings.add(singleDto.getBookingObject()));
        }

        return listOfBookings;
    }

    /**
     * Set fields of this object if not exists in this objects that is present
     * in given booking.
     * If the string start and end time is present that start and end date from
     * booking object is not set because this will lead to inconsistent state
     * of this object.
     * If given booking object is null, then method returns without
     * any exception.
     *
     * @param booking the given booking
     */
    public void setFieldFromBookingIfNotExists(Booking booking) {
        if (booking == null) {

            return;
        }
        id = (id == null) ? booking.getIdBook() : id;
        child = (child == null) ? booking.getChild() : child;
        room = (room == null) ? booking.getRoom() : room;
        user = (user == null) ? booking.getUser() : user;
        dateStartTime = (startTime == null && dateStartTime == null) ?
                booking.getBookingStartTime() : dateStartTime;
        dateEndTime = (endTime == null && dateEndTime == null) ?
                booking.getBookingEndTime() : dateEndTime;
        comment = (comment == null) ? booking.getComment() : comment;
        bookingState = (bookingState == null) ? booking.getBookingState() : bookingState;
        durationLong = (durationLong == null) ?  booking.getDuration() : durationLong;
        sum = (sum == null) ? booking.getSum() : sum;
        recurrentId = (recurrentId == null) ? booking.getRecurrentId() : recurrentId;
    }

    /**
     * Set all absent id from given booking for this object.
     *
     * @param booking the given booking
     */
    public void setAllAbsentIdFromBooking(Booking booking) {
        if (booking == null) {

            return;
        }
        id = booking.getIdBook();
        idChild = booking.getChild() != null ? booking.getChild().getId() : idChild;
        userId = booking.getUser() != null ? booking.getUser().getId() : userId;
        roomId = booking.getRoom() != null ? booking.getRoom().getId() : roomId;
        kidId = idChild;
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
        if (startTime == null) {
            DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
            startTime = df.format(dateStartTime);
        }
    }

    public Date getDateEndTime() {

        return dateEndTime;
    }

    public void setDateEndTime(Date dateEndTime) {
        this.dateEndTime = dateEndTime;
        if (endTime == null) {
            DateFormat df = new SimpleDateFormat(DateConstants.DATE_FORMAT);
            endTime = df.format(dateEndTime);
        }
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

    public void setWeekDays(Set<Integer> weekDays) {

        this.weekDays = weekDays;
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

    /**
     * Sets the endTime in the format HH:mm from Date object
     *
     * @param endTime the given Date object
     */
    public void setRightEndTime(Date endTime) {
        DateFormat df = new SimpleDateFormat(DateConstants.TIME_FORMAT);
        this.endTime = df.format(endTime);
    }

    /**
     * Sets the endDate in the format YYYY-MM-dd from Date object
     *
     * @param endDate the given Date object
     */
    public void setRightEndDate(Date endDate) {
        DateFormat df = new SimpleDateFormat(DateConstants.SHORT_DATE_FORMAT);
        this.endDate = df.format(endDate);
    }

}
