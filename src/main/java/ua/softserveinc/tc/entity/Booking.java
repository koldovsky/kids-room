package ua.softserveinc.tc.entity;

import ua.softserveinc.tc.entity.ColumnConstants.BookingConst;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TARAS on 30.04.2016.
 */
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BookingConst.ID_BOOK, nullable = false)
    private Long idBook;

    @ManyToOne
    @JoinColumn//(name = "id_child")
    @Column(name = BookingConst.ID_CHILD, nullable = false)
    private Child idChild;
    //private Long idChild;

    @ManyToOne
    @JoinColumn//(name = "id_room")
    @Column(name = BookingConst.ID_ROOM, nullable = false)
    private Room idRoom;
    //private Long idRoom;

    @ManyToOne
    @JoinColumn//(name = "id_user")
    @Column(name = BookingConst.ID_USER, nullable = false)
    private User idUser;
    //private Long idUser

    @Column(name = BookingConst.BOOKING_START_TIME, nullable = false)
    private Date bookingStartTime;

    @Column(name = BookingConst.BOOKING_END_TIME, nullable = false)
    private Date bookingEndTime;

    @Column(name = BookingConst.COMMENT)
    private String comment;

    @Column(name = BookingConst.IS_CANCELED, nullable = false)
    private boolean isCanceled;


    public Booking() {
    }

    public Booking(Child idChild, Room idRoom, User idUser, Date bookingStartTime, Date bookingEndTime,
                   String comment, boolean isCanceled) {
        this.idChild = idChild;
        this.idRoom = idRoom;
        this.idUser = idUser;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.comment = comment;
        this.isCanceled = isCanceled;
    }

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
}
