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
        monthAndDay += String.format("%02d", (calendar.get(Calendar.MONTH)) + 1) + ".";
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

    public String getDifference()
    {
        String difference = "";

        Calendar calendar = DateToCalendar(bookingStartTime);
        int startHour = calendar.get(Calendar.HOUR_OF_DAY);
        int startMinute = calendar.get(Calendar.MINUTE);

        calendar.setTime(bookingEndTime);
        int endHour = calendar.get(Calendar.HOUR_OF_DAY);
        int endMinute = calendar.get(Calendar.MINUTE);

        int differenceHour = endHour - startHour;
        int differenceMinute = endMinute - startMinute;
        if (differenceMinute < 0) {differenceHour--; differenceMinute += 60;}

        difference += String.format("%02d", differenceHour) + ":";
        difference += String.format("%02d", differenceMinute);
        return difference;
    }

    public int getPrice(String hoursAndMinutes)
    {
        /*
        * this method uses getDifference() from class Booking
        * getDifference() returns string in which first two characters represent hour
        * so we have to substring first two characters in order to parse
        */
        int time = Integer.parseInt(hoursAndMinutes.substring(0, 2));

        // later we create list and sort it in order to choose appropriate hour
        ArrayList<Integer> listOfKeys = new ArrayList<>();
        for (Integer key : getIdRoom().getPricing().keySet()) {listOfKeys.add(key);}
        Collections.sort(listOfKeys);

        // in case manager inputed value that is bigger than max value from list
        int count = 0;
        while (true)
        {
            if (listOfKeys.contains(time)) return getIdRoom().getPricing().get(time);
            time++;
            count++;
            if (count > 10) return getIdRoom().getPricing().get(listOfKeys.get(listOfKeys.size() - 1));
        }
    }

    public static int getSum(List<Booking> bookings)
    {
        int sum = 0;
        for (Booking booking : bookings)
        {
            sum += booking.getPrice(booking.getDifference());
        }
        return sum;
    }
}
