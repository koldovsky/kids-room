package ua.softserveinc.tc.entity;

import org.hibernate.annotations.GenericGenerator;
import ua.softserveinc.tc.constants.ColumnConstants.BookingConst;
import ua.softserveinc.tc.constants.ColumnConstants.ChildConst;
import ua.softserveinc.tc.constants.ColumnConstants.RoomConst;
import ua.softserveinc.tc.constants.ColumnConstants.UserConst;

import javax.persistence.*;
import java.util.*;

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

    @Column(name = BookingConst.IS_CANCELLED, nullable = false)
    private boolean isCancelled;

    @Column(name = BookingConst.SUM, columnDefinition = "int default 0")
    private int sum;

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

    public Date getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(Date bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public Date getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Date bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String extractMonthDayAndYear() //TODO for Vasyl: Remove when unused
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingStartTime);
        String monthAndDay = calendar.get(Calendar.YEAR)+ "-";
        monthAndDay += String.format("%02d", (calendar.get(Calendar.MONTH)) + 1) + "-";
        monthAndDay += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        return monthAndDay;
    }

    public String extractHourAndMinuteFromStartTime()//TODO for Vasyl: Remove when unused
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingStartTime);
        String hourAndMinute = "";
        hourAndMinute += String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":";
        hourAndMinute += String.format("%02d", calendar.get(Calendar.MINUTE));
        return hourAndMinute;
    }

    public String getDuration()// Чи обов'язково переносити в сервіс?
    {
        long difference = bookingEndTime.getTime() - bookingStartTime.getTime();
        long hours = difference / 1000 / 60 / 60;
        long minutes = difference / 1000 / 60 % 60;
        String duration = String.format("%02d", hours) + ":";
        duration += String.format("%02d", minutes);

        return duration;
    }
}
