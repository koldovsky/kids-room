package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.ColumnConstants.BookingConst;
import ua.softserveinc.tc.constants.ColumnConstants.ChildConst;
import ua.softserveinc.tc.constants.ColumnConstants.RoomConst;
import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TARAS on 30.04.2016.
 */
@Entity
@Table(name = BookingConst.TABLE_NAME_BOOKING)
public class Booking {

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = BookingConst.ID_BOOK, nullable = false)
    private Long idBook;

    @ManyToOne(optional = false)//(cascade = CascadeType.ALL)
    @JoinColumn(name = ChildConst.ID_CHILD)
    private Child idChild;

    @ManyToOne(optional = false)//(fetch = FetchType.LAZY)
    @JoinColumn(name = RoomConst.ID_ROOM)
    private Room idRoom;

    @ManyToOne(optional = false)
    @JoinColumn(name = UserConst.ID_USER)
    private User idUser;

    @Column(name = BookingConst.BOOKING_START_TIME, nullable = false)
    private Date bookingStartTime;

    @Column(name = BookingConst.BOOKING_END_TIME, nullable = false)
    private Date bookingEndTime;

    @Column(name = BookingConst.COMMENT)
    private String comment;

    @Column(name = BookingConst.IS_CANCELED, nullable = false)
    private boolean isCanceled;

    public Date getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Date bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public Date getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(Date bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }

    public Child getIdChild() {
        return idChild;
    }

    public void setIdChild(Child idChild) {
        this.idChild = idChild;
    }

    public Room getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Room idRoom) {
        this.idRoom = idRoom;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public static Calendar DateToCalendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public int extractYear()
    {
        int year = DateToCalendar(bookingStartTime).get(Calendar.YEAR);
        return year;
    }

    public int extractMonth()
    {
        int month = DateToCalendar(bookingStartTime).get(Calendar.MONTH) + 1;
        return month;
    }

    public int extractDay()
    {
        int day = DateToCalendar(bookingStartTime).get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public int extractHourFromStartTime()
    {
        int hour = DateToCalendar(bookingStartTime).get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public int extractHourFromEndTime()
    {
        int hour = DateToCalendar(bookingEndTime).get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    public int extractMinuteFromStartTime()
    {
        int minute = DateToCalendar(bookingStartTime).get(Calendar.MINUTE);
        return minute;
    }

    public int extractMinuteFromEndTime()
    {
        int minute = DateToCalendar(bookingEndTime).get(Calendar.MINUTE);
        return minute;
    }

    public String extractMonthAndDay()
    {
        Calendar calendar = DateToCalendar(bookingStartTime);
        String monthAndDay = "";
        monthAndDay += (calendar.get(Calendar.MONTH) + 1) + ".";
        monthAndDay += calendar.get(Calendar.DAY_OF_MONTH);
        return monthAndDay;
    }

    public String extractHourAndMinuteFromStartTime()
    {
        Calendar calendar = DateToCalendar(bookingStartTime);
        String hourAndMinute = "";
        hourAndMinute += calendar.get(Calendar.HOUR_OF_DAY) + ":";
        hourAndMinute += calendar.get(Calendar.MINUTE);
        return hourAndMinute;
    }

    public String extractHourAndMinuteFromEndTime()
    {
        Calendar calendar = DateToCalendar(bookingEndTime);
        String hourAndMinute = "";
        hourAndMinute += calendar.get(Calendar.HOUR_OF_DAY) + ":";
        hourAndMinute += calendar.get(Calendar.MINUTE);
        return hourAndMinute;
    }
}
