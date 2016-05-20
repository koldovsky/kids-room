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

    @Column(name = BookingConst.IS_CONFIRMED, nullable = false,
            columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isConfirmed;

    @Column(name = BookingConst.DURATION)
    private String duration;

    //TODO: переробити на лонг
    @Column(name = BookingConst.SUM, columnDefinition = "int default 0")
    private long sum;

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

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getSum() {
        return sum;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public String getDuration()// TODO: придумати як рахувати суму, як бути з цим методом і з полем sum
    {
        long difference = bookingEndTime.getTime() - bookingStartTime.getTime();
        long hours = difference / 1000 / 60 / 60;
        long minutes = difference / 1000 / 60 % 60;
        String duration = String.format("%02d", hours) + ":";
        duration += String.format("%02d", minutes);

        return duration;
    }

    public void calculateSum() // Тимчасово тут, поки немає готового методу, який
    // викликається, коли менеджер змінює час прибуття
    {
        String hoursAndMinutes = this.getDuration();
        // extract hour and minute from passed String
        int hours = Integer.parseInt(hoursAndMinutes.substring(0, 2));
        int minutes = Integer.parseInt(hoursAndMinutes.substring(3));

        // 02:00 hours - 2 hours; 02:01 hours - 3 hours
        if (minutes > 0) hours++;

        // get prices for particular room and sort them in order to choose appropriate one
        Map<Integer, Long> prices = idRoom.getPrices();
        ArrayList<Integer> listOfKeys = new ArrayList<>();
        listOfKeys.addAll(prices.keySet());
        Collections.sort(listOfKeys);

        while (true)
        {
            if (listOfKeys.contains(hours)){
                this.sum = prices.get(hours);
                break;
            }
            hours++;
            // if manager enters value that is bigger than max value in the list
            // we return price for max value
            if (hours > 10) {
                this.sum = prices.get(listOfKeys.get(listOfKeys.size() - 1));
                break;
            }
        }
    }

    public void confirm(){
        this.isConfirmed = true;
        this.calculateSum();

    }
}
