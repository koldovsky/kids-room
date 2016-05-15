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

    @Column(name = BookingConst.IS_CANCELED, nullable = false)
    private boolean isCanceled;

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

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    private static Calendar DateToCalendar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public int extractYear()
    {
        return DateToCalendar(bookingStartTime).get(Calendar.YEAR);
    }

    public int extractMonth()
    {
        return DateToCalendar(bookingStartTime).get(Calendar.MONTH) + 1;
    }

    public int extractDay()
    {
        return DateToCalendar(bookingStartTime).get(Calendar.DAY_OF_MONTH);
    }

    public int extractHourFromStartTime()
    {
        return DateToCalendar(bookingStartTime).get(Calendar.HOUR_OF_DAY);
    }

    public int extractHourFromEndTime()
    {
        return DateToCalendar(bookingEndTime).get(Calendar.HOUR_OF_DAY);
    }

    public int extractMinuteFromStartTime()
    {
        return DateToCalendar(bookingStartTime).get(Calendar.MINUTE);
    }

    public int extractMinuteFromEndTime()
    {
        return DateToCalendar(bookingEndTime).get(Calendar.MINUTE);
    }

    public String extractMonthAndDay()
    {
        Calendar calendar = DateToCalendar(bookingStartTime);
        String monthAndDay = "";
        monthAndDay += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)) + "/";
        monthAndDay += String.format("%02d", (calendar.get(Calendar.MONTH)) + 1);
        return monthAndDay;
    }
    public String extractMonthDayAndYear()
    {
        Calendar calendar = DateToCalendar(bookingStartTime);
        String monthAndDay = "";
        monthAndDay += this.extractYear()+"-";
        monthAndDay += String.format("%02d", (calendar.get(Calendar.MONTH)) + 1) + "-";
        monthAndDay += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
        return monthAndDay;
    }



    public String extractHourAndMinuteFromStartTime()
    {
        Calendar calendar = DateToCalendar(bookingStartTime);
        String hourAndMinute = "";
        hourAndMinute += String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":";
        hourAndMinute += String.format("%02d", calendar.get(Calendar.MINUTE));
        return hourAndMinute;
    }

    public String extractHourAndMinuteFromEndTime()
    {
        Calendar calendar = DateToCalendar(bookingEndTime);
        String hourAndMinute = "";
        hourAndMinute += String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":";
        hourAndMinute += String.format("%02d", calendar.get(Calendar.MINUTE));
        return hourAndMinute;
    }

    public String getDuration()
    {
        String duration = "";

        Calendar calendar = DateToCalendar(bookingStartTime);
        int startHour = calendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = calendar.get(Calendar.MINUTE);

        calendar.setTime(bookingEndTime);
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMinute = calendar.get(Calendar.MINUTE);

        int differenceHour = endHour - startHour;
        int differenceMinute = endMinute - startMinute;
        if (differenceMinute < 0) {differenceHour--; differenceMinute += 60;}

        duration += String.format("%02d", differenceHour) + ":";
        duration += String.format("%02d", differenceMinute);
        return duration;
    }

    public int getSum(String hoursAndMinutes)
    {
        // extract hour and minute from passed String
        int hours = Integer.parseInt(hoursAndMinutes.substring(0, 2));
        int minutes = Integer.parseInt(hoursAndMinutes.substring(3));

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        // get prices for particular room and sort them in order to choose appropriate one
        Map<Integer, Integer> prices = idRoom.getPrices();
        ArrayList<Integer> listOfKeys = new ArrayList<>();
        listOfKeys.addAll(prices.keySet());
        Collections.sort(listOfKeys);

        while (true)
        {
            if (listOfKeys.contains(hours)) return prices.get(hours);
            hours++;
            // if manager enters value that is bigger than max value in the list
            // we return price for max value
            if (hours > 10) return prices.get(listOfKeys.get(listOfKeys.size() - 1));
        }
    }

    public static int getSumTotal(List<Booking> bookings)
    {
        int sumTotal = 0;
        for (Booking booking : bookings)
        {
            sumTotal += booking.getSum(booking.getDuration());
        }

        return sumTotal;
    }
}
